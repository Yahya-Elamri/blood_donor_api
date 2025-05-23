package ump.blooddonor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ump.blooddonor.entity.Hospital;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findById(Long id);
}
