package ump.blooddonor.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nom;
    private String adresse;
    private String telephone;

    @OneToMany(mappedBy = "hospital")
    @ToString.Exclude
    @JsonManagedReference("hospital-demandes")
    private List<BloodRequest> demandes = new ArrayList<>();

    @OneToMany(mappedBy = "hospital")
    @ToString.Exclude
    @JsonManagedReference("hospital-users")
    private List<HospitalUser> staff = new ArrayList<>();
}

