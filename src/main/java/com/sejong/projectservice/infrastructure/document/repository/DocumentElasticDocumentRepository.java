package com.sejong.projectservice.infrastructure.document.repository;

import com.sejong.projectservice.infrastructure.document.entity.DocumentElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentElasticDocumentRepository extends ElasticsearchRepository<DocumentElastic, String> {
    
    // 프로젝트 ID로 문서 조회
    List<DocumentElastic> findByProjectIdAndIsDeletedFalse(Long projectId);
    
    // 문서 ID로 조회
    Optional<DocumentElastic> findByDocumentIdAndIsDeletedFalse(Long documentId);
    
    // 제목이나 내용에 키워드가 포함된 문서 검색
    List<DocumentElastic> findByTitleContainingOrContentContainingAndIsDeletedFalse(String titleKeyword, String contentKeyword);
    
    // 작성자 ID로 문서 조회
    List<DocumentElastic> findByAuthorIdAndIsDeletedFalse(String authorId);
    
    // 삭제되지 않은 모든 문서 조회
    List<DocumentElastic> findByIsDeletedFalse();
}