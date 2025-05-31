package ump.blooddonor.Controller.Donor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ump.blooddonor.Exception.ResourceNotFoundException;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.repository.DonorRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/donorsmessage")
@RequiredArgsConstructor
public class DonorController {

    private final DonorRepository donorRepository;

    @PostMapping("/{donorId}/fcm-token")
    public ResponseEntity<Void> registerFcmToken(
            @PathVariable Long donorId,
            @RequestBody String token) {

        String cleanToken = URLDecoder.decode(token, StandardCharsets.UTF_8)
                .replaceFirst("^token=", "");

        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found"));

        donor.getFcmTokens().add(cleanToken);
        donorRepository.save(donor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{donorId}/fcm-token")
    public ResponseEntity<Void> removeFcmToken(
            @PathVariable Long donorId,
            @RequestBody String token) {

        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found"));

        donor.getFcmTokens().remove(token);
        donorRepository.save(donor);
        return ResponseEntity.ok().build();
    }
}
