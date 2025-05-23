package ump.blooddonor.services;

import ump.blooddonor.Exception.ResourceNotFoundException;
import ump.blooddonor.entity.Hospital;
import ump.blooddonor.entity.HospitalUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ump.blooddonor.entity.User;
import ump.blooddonor.repository.HospitalRepository;
import ump.blooddonor.repository.HospitalUserRepository;


import java.util.List;

@Service
public class HospitalUserService {

    private final HospitalUserRepository hospitalUserRepository;
    private final HospitalRepository hospitalRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public HospitalUserService(HospitalUserRepository hospitalUserRepository,
                               HospitalRepository hospitalRepository,
                               PasswordEncoder passwordEncoder) {
        this.hospitalUserRepository = hospitalUserRepository;
        this.hospitalRepository = hospitalRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmail(String email) {
        return hospitalUserRepository.findByEmail(email).isPresent();
    }

    public HospitalUser createHospitalUser(HospitalUser dto) {
        Hospital hospital = hospitalRepository.findById(dto.getHospital().getId())
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé"));

        HospitalUser user = new HospitalUser();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(User.Role.HOSPITAL);
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setPosition(dto.getPosition());
        user.setHospital(hospital);

        return hospitalUserRepository.save(user);
    }

    public HospitalUser getHospitalUserById(Long id) {
        return hospitalUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HospitalUser not found with id: " + id));
    }

    public HospitalUser updateHospitalUser(Long id, HospitalUser dto) {
        HospitalUser user = hospitalUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur hospitalier non trouvé"));

        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setPosition(dto.getPosition());

        if(dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return hospitalUserRepository.save(user);
    }

    public List<HospitalUser> getByHospital(Long hospitalId) {
        return hospitalUserRepository.findByHospitalId(hospitalId);
    }

    public void deleteHospitalUser(Long id) {
        hospitalUserRepository.deleteById(id);
    }
}
