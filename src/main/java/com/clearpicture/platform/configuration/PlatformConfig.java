package com.clearpicture.platform.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlatformConfig {

    private static final Logger log = LoggerFactory.getLogger(PlatformConfig.class);

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService(AWSCredentialsProvider credentialsProvider,
                                                             @Value("${cloud.aws.region.static}") String region) {
        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
    }

    @Bean
    public SimpleEmailServiceJavaMailSender simpleEmailServiceJavaMailSender(AmazonSimpleEmailService ses) {
        return new SimpleEmailServiceJavaMailSender(ses);
    }
}
