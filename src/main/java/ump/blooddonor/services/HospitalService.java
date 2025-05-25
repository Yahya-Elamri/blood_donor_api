package ump.blooddonor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ump.blooddonor.Exception.ResourceNotFoundException;
import ump.blooddonor.entity.Hospital;
import ump.blooddonor.repository.HospitalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + id));
    }

    public Hospital updateHospital(Long id, Hospital hospitalDetails) {
        Hospital hospital = getHospitalById(id);
        hospital.setNom(hospitalDetails.getNom());
        hospital.setAdresse(hospitalDetails.getAdresse());
        hospital.setTelephone(hospitalDetails.getTelephone());
        return hospitalRepository.save(hospital);
    }

    public void deleteHospital(Long id) {
        hospitalRepository.delete(getHospitalById(id));
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public List<Hospital> searchByName(String nom) {
        return hospitalRepository.findByNomContainingIgnoreCase(nom);
    }
}

