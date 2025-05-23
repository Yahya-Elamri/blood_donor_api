package ump.blooddonor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ump.blooddonor.entity.Donor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor,Long> {
    Optional<Donor> findByEmail(String email);
    List<Donor> findByGroupeSanguin(String groupeSanguin);
    List<Donor> findByDernierDonBeforeOrDernierDonIsNull(LocalDate date);
}
