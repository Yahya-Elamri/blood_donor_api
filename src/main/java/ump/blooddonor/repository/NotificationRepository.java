package ump.blooddonor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ump.blooddonor.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByDonorId(Long donorId);
}
