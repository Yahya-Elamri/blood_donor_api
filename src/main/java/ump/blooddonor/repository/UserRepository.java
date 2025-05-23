package ump.blooddonor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import ump.blooddonor.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
