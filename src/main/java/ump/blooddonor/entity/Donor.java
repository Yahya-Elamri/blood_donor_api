package ump.blooddonor.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Donor extends User {
    @Enumerated(EnumType.STRING)
    private BloodType groupeSanguin;

    private LocalDate dernierDon;
    private String localisation;

    @OneToMany(mappedBy = "donor")
    @ToString.Exclude
    @JsonManagedReference("donor-donations")
    private List<Donation> donations = new ArrayList<>();
}
