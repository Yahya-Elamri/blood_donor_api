package ump.blooddonor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ump.blooddonor.Exception.ResourceNotFoundException;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.entity.Notification;
import ump.blooddonor.repository.DonorRepository;
import ump.blooddonor.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final DonorRepository donorRepository;

    public Notification createNotification(Notification notification, Long donorId) {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found"));
        notification.setDonor(donor);
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByDonor(Long donorId) {
        return notificationRepository.findByDonorId(donorId);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
