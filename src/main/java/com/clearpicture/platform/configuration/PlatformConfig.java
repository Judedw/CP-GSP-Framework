package com.clearpicture.platform.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.clearpicture.platform.util.PlatformConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class PlatformConfig {

    private static final Logger log = LoggerFactory.getLogger(PlatformConfig.class);

    @Value("${spring.application.name}")
    private String appName;

    // THIS  SHOULD BE REMOVED SINCE NEED TO ADD THIS TO USER SERVICE FOR FETCH DATA FROM DB : RAVEEN
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


//    @Primary
//    @Bean
//    CorsConfigurationSource corsConfigurationSource(
//            @Value("#{'${app.auth.corsAllowedOrigins}'.split(',')}") List<String> allowedOrigins) {
//
//        if (!CollectionUtils.isEmpty(allowedOrigins)) {
//            log.info("CORS allowed origins: {}",
//                    allowedOrigins.stream().collect(Collectors.joining(PlatformConstant.COMMA_DELIMITER)));
//        }else{
//            log.info("CORS origins empty");
//        }
//
//
//        CorsConfiguration configuration = new CorsConfiguration();
//        //configuration.setAllowedOrigins(allowedOrigins);
//        configuration.addAllowedOrigin("*");
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        configuration.addExposedHeader("Authorization");
//        configuration.addExposedHeader("Content-Type");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
