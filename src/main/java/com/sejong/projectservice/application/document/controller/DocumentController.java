package com.sejong.projectservice.application.document.controller;

import com.sejong.projectservice.application.document.service.DocumentService;
import com.sejong.projectservice.infrastructure.document.entity.DocumentElastic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    
    private final DocumentService documentService;
    
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    
    /**
     * 문서 검색
     */
    @GetMapping("/search")
    public ResponseEntity<List<DocumentElastic>> searchDocuments(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long projectId) {
        
        List<DocumentElastic> documents = documentService.searchDocuments(keyword, projectId);
        return ResponseEntity.ok(documents);
    }
    
    /**
     * 프로젝트별 문서 조회
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<DocumentElastic>> getDocumentsByProject(@PathVariable Long projectId) {
        List<DocumentElastic> documents = documentService.getDocumentsByProjectId(projectId);
        return ResponseEntity.ok(documents);
    }
    
    /**
     * 전체 문서 조회
     */
    @GetMapping
    public ResponseEntity<List<DocumentElastic>> getAllDocuments() {
        List<DocumentElastic> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }
    
    /**
     * 문서 저장 (테스트용)
     */
    @PostMapping("/test")
    public ResponseEntity<String> saveTestDocument(
            @RequestParam Long documentId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam Long projectId,
            @RequestParam String authorId) {
        
        documentService.saveDocument(documentId, title, content, projectId, authorId);
        return ResponseEntity.ok("Document saved successfully");
    }
    
    /**
     * 문서 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }
}