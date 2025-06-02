package ump.blooddonor.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.entity.User;
import ump.blooddonor.repository.DonorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DonorService(DonorRepository donorRepository, PasswordEncoder passwordEncoder) {
        this.donorRepository = donorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Vérifie l'existence par email
    public boolean existsByEmail(String email) {
        return donorRepository.findByEmail(email).isPresent();
    }

    // Création d'un donneur
    public Donor createDonor(Donor donorDto) {
        Donor donor = new Donor();
        donor.setEmail(donorDto.getEmail());
        donor.setPassword(passwordEncoder.encode(donorDto.getPassword()));
        donor.setRole(User.Role.DONOR);
        donor.setNom(donorDto.getNom());
        donor.setPrenom(donorDto.getPrenom());
        donor.setGroupeSanguin(donorDto.getGroupeSanguin());
        donor.setDernierDon(donorDto.getDernierDon());
        donor.setLocalisation(donorDto.getLocalisation());

        return donorRepository.save(donor);
    }

    // Récupération de tous les donneurs
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    // Récupération par ID
    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donneur non trouvé avec l'ID: " + id));
    }

    public Optional<Donor> getDonorByEmail(String email) {
        return donorRepository.findByEmail(email);
    }

    // Mise à jour du donneur
    public Donor updateDonor(Long id, Donor donorDto) {
        Donor existingDonor = getDonorById(id);

        // Mise à jour des champs
        existingDonor.setNom(donorDto.getNom());
        existingDonor.setPrenom(donorDto.getPrenom());
        existingDonor.setGroupeSanguin(donorDto.getGroupeSanguin());
        existingDonor.setLocalisation(donorDto.getLocalisation());

        // Mise à jour conditionnelle du mot de passe
        if (donorDto.getPassword() != null && !donorDto.getPassword().isEmpty()) {
            existingDonor.setPassword(passwordEncoder.encode(donorDto.getPassword()));
        }

        // Mise à jour date dernier don
        if (donorDto.getDernierDon() != null) {
            existingDonor.setDernierDon(donorDto.getDernierDon());
        }

        return donorRepository.save(existingDonor);
    }

    // Suppression d'un donneur
    public void deleteDonor(Long id) {
        Donor donor = getDonorById(id);
        donorRepository.delete(donor);
    }

    // Méthodes supplémentaires
    public List<Donor> getDonorsByBloodGroup(String groupeSanguin) {
        return donorRepository.findByGroupeSanguin(groupeSanguin);
    }

    public List<Donor> getAvailableDonors(LocalDate depuisDate) {
        return donorRepository.findByDernierDonBeforeOrDernierDonIsNull(depuisDate);
    }
}
