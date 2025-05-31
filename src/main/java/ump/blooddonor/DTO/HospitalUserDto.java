package ump.blooddonor.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ump.blooddonor.entity.Hospital;
import ump.blooddonor.entity.HospitalUser;

@Data
@NoArgsConstructor
public class HospitalUserDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String position;
    private Long hospitalId;
    private HospitalDto hospital;

    public HospitalUserDto(HospitalUser user) {
        this.id = user.getId();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.email = user.getEmail();
        this.position = user.getPosition();
        this.hospitalId = user.getHospital() != null ? user.getHospital().getId() : null;
        this.hospital = user.getHospital() != null ? new HospitalDto(user.getHospital()) : null;
    }
}
