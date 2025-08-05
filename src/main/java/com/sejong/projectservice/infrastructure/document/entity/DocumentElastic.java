package com.sejong.projectservice.infrastructure.document.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;

@Document(indexName = "documents")
@Setting(settingPath = "elasticsearch/document-settings.json")
public class DocumentElastic {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Long)
    private Long documentId;
    
    @Field(type = FieldType.Text, analyzer = "korean")
    private String title;
    
    @Field(type = FieldType.Text, analyzer = "korean")
    private String content;
    
    @Field(type = FieldType.Long)
    private Long projectId;
    
    @Field(type = FieldType.Keyword)
    private String authorId;
    
    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;
    
    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;
    
    @Field(type = FieldType.Boolean)
    private Boolean isDeleted;
    
    // 기본 생성자
    public DocumentElastic() {
        this.isDeleted = false;
    }
    
    // 모든 필드를 포함한 생성자
    public DocumentElastic(Long documentId, String title, String content, Long projectId, 
                          String authorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.documentId = documentId;
        this.title = title;
        this.content = content;
        this.projectId = projectId;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = false;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Long getDocumentId() {
        return documentId;
    }
    
    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Long getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public String getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Boolean getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}