package com.sejong.projectservice.infrastructure.document.repository;

import com.sejong.projectservice.core.document.repository.DocumentElasticRepository;
import com.sejong.projectservice.infrastructure.document.entity.DocumentElastic;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DocumentElasticRepositoryImpl implements DocumentElasticRepository {
    
    private final ElasticsearchOperations elasticsearchOperations;
    private final DocumentElasticDocumentRepository documentElasticDocumentRepository;
    
    public DocumentElasticRepositoryImpl(ElasticsearchOperations elasticsearchOperations,
                                       DocumentElasticDocumentRepository documentElasticDocumentRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.documentElasticDocumentRepository = documentElasticDocumentRepository;
    }
    
    @Override
    public List<DocumentElastic> searchDocuments(String keyword, Long projectId) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("isDeleted", false));
        
        // 프로젝트 ID 필터 추가
        if (projectId != null) {
            boolQuery.must(QueryBuilders.termQuery("projectId", projectId));
        }
        
        // 키워드 검색 추가
        if (keyword != null && !keyword.trim().isEmpty()) {
            boolQuery.must(
                QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title", keyword).boost(2.0f))
                    .should(QueryBuilders.matchQuery("content", keyword))
                    .minimumShouldMatch(1)
            );
        }
        
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(PageRequest.of(0, 100))
                .build();
        
        SearchHits<DocumentElastic> searchHits = elasticsearchOperations.search(searchQuery, DocumentElastic.class);
        
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DocumentElastic> findByProjectId(Long projectId) {
        return documentElasticDocumentRepository.findByProjectIdAndIsDeletedFalse(projectId);
    }
    
    @Override
    public void saveDocument(DocumentElastic document) {
        documentElasticDocumentRepository.save(document);
    }
    
    @Override
    public void deleteDocument(String id) {
        documentElasticDocumentRepository.findById(id).ifPresent(doc -> {
            doc.setIsDeleted(true);
            documentElasticDocumentRepository.save(doc);
        });
    }
    
    @Override
    public List<DocumentElastic> findAll() {
        return documentElasticDocumentRepository.findByIsDeletedFalse();
    }
    
    @Override
    public void refreshIndex() {
        elasticsearchOperations.indexOps(DocumentElastic.class).refresh();
    }
}