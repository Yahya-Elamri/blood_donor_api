package ump.blooddonor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ump.blooddonor.entity.BloodType;
import ump.blooddonor.entity.Donor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor,Long> {
    Optional<Donor> findByEmail(String email);
    List<Donor> findByGroupeSanguin(String groupeSanguin);
    List<Donor> findByDernierDonBeforeOrDernierDonIsNull(LocalDate date);
    @EntityGraph("Donor.withFcmTokens")
    List<Donor> findAllByGroupeSanguin(BloodType bloodType);
}
