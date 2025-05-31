package ump.blooddonor.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ump.blooddonor.DTO.HospitalUserDto;
import ump.blooddonor.entity.HospitalUser;
import ump.blooddonor.services.HospitalUserService;

import java.util.List;

@RestController
@RequestMapping("/api/requests/hospital-users")
public class HospitalUserController {

    private final HospitalUserService hospitalUserService;

    @Autowired
    public HospitalUserController(HospitalUserService hospitalUserService) {
        this.hospitalUserService = hospitalUserService;
    }

    // Create a new hospital user
    @PostMapping
    public ResponseEntity<HospitalUser> createHospitalUser(@RequestBody HospitalUser user) {
        HospitalUser createdUser = hospitalUserService.createHospitalUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Get hospital user by ID
    @GetMapping("/{id}")
    public ResponseEntity<HospitalUserDto> getHospitalUser(@PathVariable Long id) {
        HospitalUser user = hospitalUserService.getHospitalUserById(id);
        HospitalUserDto dto = new HospitalUserDto(user);
        System.out.println(dto.toString());
        return ResponseEntity.ok(dto);
    }

    // Update hospital user
    @PutMapping("/{id}")
    public ResponseEntity<HospitalUser> updateHospitalUser(@PathVariable Long id,
                                                           @RequestBody HospitalUser userDetails) {
        HospitalUser updatedUser = hospitalUserService.updateHospitalUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete hospital user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospitalUser(@PathVariable Long id) {
        hospitalUserService.deleteHospitalUser(id);
        return ResponseEntity.noContent().build();
    }

    // Get all users for a hospital
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<HospitalUser>> getUsersByHospital(@PathVariable Long hospitalId) {
        List<HospitalUser> users = hospitalUserService.getByHospital(hospitalId);
        return ResponseEntity.ok(users);
    }

    // Check if email exists
    @GetMapping("/exists")
    public ResponseEntity<Boolean> emailExists(@RequestParam String email) {
        boolean exists = hospitalUserService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
