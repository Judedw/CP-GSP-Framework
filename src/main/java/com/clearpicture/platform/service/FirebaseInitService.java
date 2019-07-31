package com.clearpicture.platform.service;


/**
 * created by Raveen -  2019/07/19
 * <p>
 * FirebaseInitService - Firebase Cloud Messaging Service Initializing
 */


import com.clearpicture.platform.exception.ComplexValidationException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.logging.Logger;


@Service
public class FirebaseInitService {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    Logger LOGGER = Logger.getLogger(FirebaseInitService.class.getName());

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                LOGGER.info("Firebase Service has been initialized");
            }
        } catch (IOException e) {
            LOGGER.info("Error in Firebase Initializing ");
            e.printStackTrace();
            throw new ComplexValidationException("firebase", "errorInInit");
        }
    }

}
