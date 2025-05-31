package ump.blooddonor.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ump.blooddonor.services.FirebaseMessagingService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    private static final Logger log = LoggerFactory.getLogger(FirebaseMessagingService.class);

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        try {
            // Verify resource exists
            ClassPathResource resource = new ClassPathResource("firebase-service-account.json");
            if (!resource.exists()) {
                throw new FileNotFoundException("Firebase service account file not found in classpath");
            }

            InputStream serviceAccount = resource.getInputStream();
            log.info("Successfully loaded Firebase service account file");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);
            log.info("Initialized Firebase app: {}", app.getName());

            return FirebaseMessaging.getInstance(app);
        } catch (IOException e) {
            log.error("Failed to initialize FirebaseMessaging", e);
            throw e;
        }
    }
}