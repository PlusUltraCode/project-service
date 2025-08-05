package com.sejong.projectservice.application.document.service;

import com.sejong.projectservice.core.document.repository.DocumentElasticRepository;
import com.sejong.projectservice.infrastructure.document.entity.DocumentElastic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DocumentService {
    
    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    
    private final DocumentElasticRepository documentElasticRepository;
    
    public DocumentService(DocumentElasticRepository documentElasticRepository) {
        this.documentElasticRepository = documentElasticRepository;
    }
    
    /**
     * 키워드로 문서 검색
     */
    public List<DocumentElastic> searchDocuments(String keyword, Long projectId) {
        log.debug("Searching documents with keyword: '{}', projectId: {}", keyword, projectId);
        
        try {
            // 인덱스 리프레시 (테스트용)
            documentElasticRepository.refreshIndex();
            
            List<DocumentElastic> results = documentElasticRepository.searchDocuments(keyword, projectId);
            log.debug("Found {} documents", results.size());
            
            return results;
        } catch (Exception e) {
            log.error("Error searching documents", e);
            throw new RuntimeException("문서 검색 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 프로젝트 ID로 문서 조회
     */
    public List<DocumentElastic> getDocumentsByProjectId(Long projectId) {
        log.debug("Getting documents for projectId: {}", projectId);
        
        try {
            // 인덱스 리프레시 (테스트용)
            documentElasticRepository.refreshIndex();
            
            List<DocumentElastic> results = documentElasticRepository.findByProjectId(projectId);
            log.debug("Found {} documents for project {}", results.size(), projectId);
            
            return results;
        } catch (Exception e) {
            log.error("Error getting documents by projectId", e);
            throw new RuntimeException("프로젝트 문서 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 모든 문서 조회
     */
    public List<DocumentElastic> getAllDocuments() {
        log.debug("Getting all documents");
        
        try {
            // 인덱스 리프레시 (테스트용)
            documentElasticRepository.refreshIndex();
            
            List<DocumentElastic> results = documentElasticRepository.findAll();
            log.debug("Found {} total documents", results.size());
            
            return results;
        } catch (Exception e) {
            log.error("Error getting all documents", e);
            throw new RuntimeException("전체 문서 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 문서 저장
     */
    public void saveDocument(Long documentId, String title, String content, 
                           Long projectId, String authorId) {
        log.debug("Saving document: documentId={}, title='{}', projectId={}", 
                 documentId, title, projectId);
        
        try {
            DocumentElastic document = new DocumentElastic(
                documentId, title, content, projectId, authorId,
                LocalDateTime.now(), LocalDateTime.now()
            );
            
            documentElasticRepository.saveDocument(document);
            
            // 저장 후 즉시 인덱스 리프레시
            documentElasticRepository.refreshIndex();
            
            log.info("Document saved successfully: {}", documentId);
        } catch (Exception e) {
            log.error("Error saving document", e);
            throw new RuntimeException("문서 저장 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 문서 삭제 (소프트 삭제)
     */
    public void deleteDocument(String id) {
        log.debug("Deleting document: {}", id);
        
        try {
            documentElasticRepository.deleteDocument(id);
            documentElasticRepository.refreshIndex();
            
            log.info("Document deleted successfully: {}", id);
        } catch (Exception e) {
            log.error("Error deleting document", e);
            throw new RuntimeException("문서 삭제 중 오류가 발생했습니다.", e);
        }
    }
}