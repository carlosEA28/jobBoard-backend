package br.com.carlos.JobBoard_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import java.time.Duration;

@Service
public class CompanyLogoService {

    @Value("${spring.cloudflare.secret_access_key}")
    private String secretAccessKey;

    @Value("${spring.cloudflare.access_key_id}")
    private String accessKey;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${spring.cloudflare.url}")
    private String url;

    public String generatePreSignedUrl(String objectKey) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretAccessKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        // Criando um S3Presigner para gerar a URL
        try (S3Presigner presigner = S3Presigner.builder()
                .region(Region.of("wnam"))
                .endpointOverride(URI.create(url))
                .credentialsProvider(credentialsProvider)
                .build()) {

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            PresignedPutObjectRequest preSignedRequest = presigner.presignPutObject(presign -> presign
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(putObjectRequest));

            return preSignedRequest.url().toString();
        }
    }
}