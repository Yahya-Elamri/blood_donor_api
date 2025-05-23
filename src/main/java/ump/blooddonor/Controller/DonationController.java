package ump.blooddonor.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ump.blooddonor.entity.Donation;
import ump.blooddonor.services.DonationService;

import java.util.List;

@RestController
@RequestMapping("/api/requests/donation")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<Donation> createDonation(
            @RequestBody Donation donation,
            @RequestParam Long donorId) {
        Donation createdDonation = donationService.createDonation(donation, donorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDonation);
    }

    @GetMapping("/by-donor/{donorId}")
    public ResponseEntity<List<Donation>> getDonationsByDonor(@PathVariable Long donorId) {
        List<Donation> donations = donationService.getDonationsByDonor(donorId);
        return ResponseEntity.ok(donations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}

