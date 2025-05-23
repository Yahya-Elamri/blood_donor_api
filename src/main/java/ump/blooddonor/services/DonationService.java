package ump.blooddonor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ump.blooddonor.Exception.ResourceNotFoundException;
import ump.blooddonor.entity.Donation;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.repository.DonationRepository;
import ump.blooddonor.repository.DonorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;

    public Donation createDonation(Donation donation, Long donorId) {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found"));
        donation.setDonor(donor);
        return donationRepository.save(donation);
    }

    public List<Donation> getDonationsByDonor(Long donorId) {
        return donationRepository.findByDonorId(donorId);
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}
