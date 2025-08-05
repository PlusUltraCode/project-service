package com.sejong.projectservice.core.document.repository;

import com.sejong.projectservice.infrastructure.document.entity.DocumentElastic;

import java.util.List;

public interface DocumentElasticRepository {
    
    // 키워드와 프로젝트 ID로 문서 검색
    List<DocumentElastic> searchDocuments(String keyword, Long projectId);
    
    // 프로젝트 ID로 문서 조회
    List<DocumentElastic> findByProjectId(Long projectId);
    
    // 문서 저장
    void saveDocument(DocumentElastic document);
    
    // 문서 삭제 (소프트 삭제)
    void deleteDocument(String id);
    
    // 모든 문서 조회
    List<DocumentElastic> findAll();
    
    // 인덱스 리프레시
    void refreshIndex();
}