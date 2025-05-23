package ump.blooddonor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ump.blooddonor.entity.BloodRequest;
import ump.blooddonor.entity.BloodType;

import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findByHospitalId(Long hospitalId);
    List<BloodRequest> findByGroupeSanguin(BloodType bloodType);
}
