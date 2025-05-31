package ump.blooddonor.services;

import com.google.firebase.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ump.blooddonor.entity.BloodRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FirebaseMessagingService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseMessagingService.class);
    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendNotificationToDonor(Set<String> tokens, BloodRequest request) {
        if (tokens.isEmpty()) return;

        // Build basic notification
        String title = "Urgent Blood Request!";
        String body = String.format(
                "Need %s blood at %s. %d units required",
                request.getGroupeSanguin(),
                request.getHospital().getNom(),
                request.getQuantite()
        );

        // Build message using HTTP v1 format
        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(new ArrayList<>(tokens))
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .putData("bloodRequestId", request.getId().toString())
                .build();

        try {
            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
            log.info("Notifications sent: {}", response.getSuccessCount());

            // Handle failed tokens
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> tokenList = new ArrayList<>(tokens); // convert Set to List to use indexing

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        log.error("Failed to send to token {}: {}",
                                tokenList.get(i),
                                responses.get(i).getException().getMessage());
                    }
                }
            }
        } catch (FirebaseMessagingException e) {
            log.error("Firebase notification failed", e);
        }
    }
}