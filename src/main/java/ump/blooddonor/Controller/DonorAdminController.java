package ump.blooddonor.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.services.DonorService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class DonorAdminController {

    private final DonorService donorService;

    @Autowired
    public DonorAdminController(DonorService donorService) {
        this.donorService = donorService;
    }

    // Create a new donor
    @PostMapping
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) {
        if (donorService.existsByEmail(donor.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Donor created = donorService.createDonor(donor);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get all donors
    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    // Get donor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        try {
            Donor donor = donorService.getDonorById(id);
            return ResponseEntity.ok(donor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Search donor by email
    @GetMapping("/search")
    public ResponseEntity<Donor> searchByEmail(@RequestParam("email") String email) {
        return donorService.getDonorByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Update donor
    @PutMapping("/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Long id, @RequestBody Donor donor) {
        try {
            Donor updated = donorService.updateDonor(id, donor);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete donor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        try {
            donorService.deleteDonor(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get donors by blood group
    @GetMapping("/blood-group/{group}")
    public List<Donor> getDonorsByBloodGroup(@PathVariable("group") String bloodGroup) {
        return donorService.getDonorsByBloodGroup(bloodGroup);
    }

    // Get available donors since a date
    @GetMapping("/available")
    public List<Donor> getAvailableDonors(@RequestParam("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sinceDate) {
        return donorService.getAvailableDonors(sinceDate);
    }
}

