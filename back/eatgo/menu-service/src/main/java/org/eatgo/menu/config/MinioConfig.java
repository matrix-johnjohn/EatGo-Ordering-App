package org.eatgo.menu.config;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio.oss")
@NoArgsConstructor
@AllArgsConstructor
public class MinioConfig {

    private String accessKey;
    private String secretKey;
    private String url;
    private String bucketName;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey,secretKey)
                .build();
    }
}
