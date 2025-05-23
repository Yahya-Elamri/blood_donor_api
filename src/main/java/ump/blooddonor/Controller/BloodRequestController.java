package ump.blooddonor.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ump.blooddonor.DTO.LoginRequest;
import ump.blooddonor.entity.BloodRequest;
import ump.blooddonor.entity.BloodType;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.entity.RequestStatus;
import ump.blooddonor.services.BloodRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/requests/bloodreq")
@RequiredArgsConstructor
public class BloodRequestController {
    private final BloodRequestService bloodRequestService;

    @PostMapping
    public ResponseEntity<BloodRequest> create(
            @RequestBody BloodRequest bloodRequest,
            @RequestParam Long hospitalId,
            @RequestParam Long createdById) {
        BloodRequest created = bloodRequestService.createBloodRequest(bloodRequest, hospitalId, createdById);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BloodRequest> updateStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status) {
        BloodRequest updated = bloodRequestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/by-hospital/{hospitalId}")
    public ResponseEntity<List<BloodRequest>> getByHospital(@PathVariable Long hospitalId) {
        List<BloodRequest> list = bloodRequestService.getRequestsByHospital(hospitalId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-blood-type")
    public ResponseEntity<List<BloodRequest>> getByBloodType(@RequestParam BloodType bloodType) {
        List<BloodRequest> list = bloodRequestService.getRequestsByBloodType(bloodType);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bloodRequestService.deleteBloodRequest(id);
        return ResponseEntity.noContent().build();
    }
}

