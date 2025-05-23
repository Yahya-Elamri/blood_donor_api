package ump.blooddonor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ump.blooddonor.entity.HospitalUser;

import java.util.List;
import java.util.Optional;

public interface HospitalUserRepository extends JpaRepository<HospitalUser, Long> {
    Optional<HospitalUser> findByEmail(String email);
    List<HospitalUser> findByHospitalId(Long hospitalId);
}