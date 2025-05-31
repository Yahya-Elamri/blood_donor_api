package ump.blooddonor.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ump.blooddonor.entity.BloodRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

@Service
public class FcmRestService {
    private static final String FCM_ENDPOINT = "https://fcm.googleapis.com/v1/projects/blooddonationapp-c95dc/messages:send";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendNotification(String token, BloodRequest request) throws IOException, InterruptedException {
        String accessToken = getAccessToken();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(FCM_ENDPOINT))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createPayload(token, request)))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("FCM error: " + response.body());
        }
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource("firebase-service-account.json").getInputStream()
        ).createScoped(Collections.singleton("https://www.googleapis.com/auth/firebase.messaging"));

        credentials.refreshIfExpired();
        return credentials.getAccessToken().getTokenValue();
    }

    private String createPayload(String token, BloodRequest request) {
        return String.format("""
            {
                "message": {
                    "token": "%s",
                    "notification": {
                        "title": "Urgent Blood Request",
                        "body": "Need %s blood at %s"
                    },
                    "data": {
                        "bloodRequestId": "%d"
                    }
                }
            }
            """, token, request.getGroupeSanguin(), request.getHospital().getNom(), request.getId());
    }
}