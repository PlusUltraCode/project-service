package com.sejong.projectservice.application.common.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.sejong.projectservice.infrastructure.document.repository")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    
    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl;
    
    @Value("${spring.elasticsearch.username:}")
    private String username;
    
    @Value("${spring.elasticsearch.password:}")
    private String password;
    
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration.ClientConfigurationBuilder builder = ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl.replace("http://", "").replace("https://", ""));
        
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            builder.withBasicAuth(username, password);
        }
        
        ClientConfiguration clientConfiguration = builder.build();
        
        return RestClients.create(clientConfiguration).rest();
    }
    
    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}