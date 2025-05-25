package ump.blooddonor.Controller.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ump.blooddonor.entity.Hospital;
import ump.blooddonor.services.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalPublicController {

    private final HospitalService hospitalService;

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
        Hospital hospital = hospitalService.getHospitalById(id);
        return ResponseEntity.ok(hospital);
    }

    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/search")
    public List<Hospital> searchHospitals(@RequestParam("name") String name) {
        return hospitalService.searchByName(name);
    }
}

