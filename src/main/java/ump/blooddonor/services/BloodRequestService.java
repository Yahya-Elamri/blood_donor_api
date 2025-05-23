package ump.blooddonor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ump.blooddonor.Exception.ResourceNotFoundException;
import ump.blooddonor.entity.*;
import ump.blooddonor.repository.BloodRequestRepository;
import ump.blooddonor.repository.HospitalRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodRequestService {
    private final BloodRequestRepository bloodRequestRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalUserService hospitalUserService;

    public BloodRequest createBloodRequest(BloodRequest bloodRequest, Long hospitalId, Long createdById) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found"));
        HospitalUser createdBy = hospitalUserService.getHospitalUserById(createdById);

        bloodRequest.setHospital(hospital);
        bloodRequest.setCreatedBy(createdBy);
        bloodRequest.setDateDemande(LocalDateTime.now());
        bloodRequest.setStatut(RequestStatus.PENDING);

        return bloodRequestRepository.save(bloodRequest);
    }

    public BloodRequest updateRequestStatus(Long id, RequestStatus status) {
        BloodRequest request = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BloodRequest not found"));
        request.setStatut(status);
        return bloodRequestRepository.save(request);
    }

    public List<BloodRequest> getRequestsByHospital(Long hospitalId) {
        return bloodRequestRepository.findByHospitalId(hospitalId);
    }

    public List<BloodRequest> getRequestsByBloodType(BloodType bloodType) {
        return bloodRequestRepository.findByGroupeSanguin(bloodType);
    }

    public void deleteBloodRequest(Long id) {
        bloodRequestRepository.deleteById(id);
    }
}
