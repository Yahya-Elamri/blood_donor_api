package ump.blooddonor.Controller.Donor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ump.blooddonor.entity.Donation;
import ump.blooddonor.services.DonationService;

import java.util.List;

@RestController
@RequestMapping("/api/donors/donation")
@RequiredArgsConstructor
public class DonationDonorController {

    private final DonationService donationService;

    @GetMapping("/by-donor/{donorId}")
    public ResponseEntity<List<Donation>> getDonationsByDonor(@PathVariable Long donorId) {
        List<Donation> donations = donationService.getDonationsByDonor(donorId);
        return ResponseEntity.ok(donations);
    }
}


