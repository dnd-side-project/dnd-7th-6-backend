package com.hot6.phopa.core.common.config;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.EC2ContainerCredentialsProviderWrapper;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.hot6.phopa.core.common.helper.EnvironmentHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class S3Config {

    @Value("${aws.s3.client.region:}")
    private String clientRegion;
    private final EnvironmentHelper environmentHelper;

    @Bean
    public AmazonS3 s3() {
        if (environmentHelper.isLocalTestProfile()) {
            return AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(new AWSCredentialsProviderChain(
                    new ProfileCredentialsProvider(),
                    new AWSStaticCredentialsProvider(
                        new BasicSessionCredentials(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY))))    // credential 을 통해 발급된 appId, appKey, appSession 기재
                .build();
        } else {
            return AmazonS3ClientBuilder.standard()
                .withCredentials(new EC2ContainerCredentialsProviderWrapper())
                .build();
        }
    }
}
