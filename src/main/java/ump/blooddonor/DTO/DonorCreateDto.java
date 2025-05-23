package ump.blooddonor.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DonorCreateDto {
    String email;
    String password;
    String bloodGroup;
    LocalDate lastDonationDate;
    String nom;
    String Prenom;
}
