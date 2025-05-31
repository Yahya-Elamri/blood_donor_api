package ump.blooddonor.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ump.blooddonor.DTO.BloodRequestCreatedEvent;
import ump.blooddonor.entity.BloodRequest;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.repository.DonorRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class BloodRequestEventListener {
    private final DonorRepository donorRepository;
    private final FirebaseMessagingService firebaseService;

    @Async
    @EventListener
    public void handleBloodRequestCreated(BloodRequestCreatedEvent event) {
        BloodRequest request = event.getBloodRequest();
        List<Donor> matchingDonors = donorRepository.findAllByGroupeSanguin(request.getGroupeSanguin());

        matchingDonors.forEach(donor -> {
            // Initialize collection within active session
            Hibernate.initialize(donor.getFcmTokens());
            Set<String> tokens = donor.getFcmTokens();

            if (tokens != null && !tokens.isEmpty()) {
                firebaseService.sendNotificationToDonor(tokens, request);
            }
        });
    }
}