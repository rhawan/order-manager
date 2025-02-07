package br.com.ordermanager.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class AwsConfiguration {
    @Value("${aws.region}")
    private String region;

    @Value("${aws.auth.access-key}")
    private String accessKey;

    @Value("${aws.auth.secret-key}")
    private String secretKey;

    @Value("${aws.endpoint}")
    private String endpoint;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder().region(Region.of(region))
                .credentialsProvider(getAwsCredentialsProvider())
                .endpointOverride(URI.create(endpoint))
                .build();
    }

    private StaticCredentialsProvider getAwsCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
    }
}
