package ump.blooddonor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BloodRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    private BloodType groupeSanguin;

    private Integer quantite;
    private LocalDateTime dateDemande;

    @Enumerated(EnumType.STRING)
    private RequestStatus statut;

    @Enumerated(EnumType.STRING)
    private UrgenceLevel urgence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    @JsonBackReference("hospital-demandes")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonBackReference("user-requests")
    private HospitalUser createdBy;
}
