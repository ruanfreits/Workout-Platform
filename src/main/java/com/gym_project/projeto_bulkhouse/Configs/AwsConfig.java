package com.gym_project.projeto_bulkhouse.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
@Configuration
public class AwsConfig {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    /**
     * Creates a reusable S3Client object, so other parts of the application can use
     * it for AWS S3 operations like uploading or downloading files
     */
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);
        
        // Configurando o S3Client com o endpoint do LocalStack
        return S3Client.builder()
                .region(Region.of(region))  // A região configurada
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .serviceConfiguration(
                    S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build()
                )
                .endpointOverride(URI.create(endpoint)) // Endpoint LocalStack
                .build();
    }
}