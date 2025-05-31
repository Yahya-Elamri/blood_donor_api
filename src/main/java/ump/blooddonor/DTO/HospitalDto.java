package ump.blooddonor.DTO;

import ump.blooddonor.entity.Hospital;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HospitalDto {
    private Long id;
    private String nom;

    public HospitalDto(Hospital hospital) {
        this.id = hospital.getId();
        this.nom = hospital.getNom();
    }
}