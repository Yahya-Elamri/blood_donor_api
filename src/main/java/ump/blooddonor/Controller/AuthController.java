package ump.blooddonor.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ump.blooddonor.DTO.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.context.SecurityContextHolder;
import ump.blooddonor.DTO.LoginRequest;
import ump.blooddonor.entity.Donor;
import ump.blooddonor.entity.HospitalUser;
import ump.blooddonor.entity.User;
import ump.blooddonor.repository.UserRepository;
import ump.blooddonor.services.*;

@RestController
@RequestMapping("/api/auth")
class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final DonorService donorService;
    private final UserService userService;
    private final HospitalUserService hospitalUserService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService, DonorService donorService, UserService userService, HospitalUserService hospitalUserService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.donorService = donorService;
        this.userService = userService;
        this.hospitalUserService = hospitalUserService;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest loginUserDto, HttpServletResponse response) {
        System.out.println("chi7aja mcriptya : "+passwordEncoder.encode(loginUserDto.getPassword()));
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        System.out.println(authenticatedUser.toString());

        String jwtToken = jwtService.generateToken(authenticatedUser,authenticatedUser.getRole().toString(),authenticatedUser.getId());

        ResponseCookie cookie = ResponseCookie.from("authToken", jwtToken)
                .httpOnly(false) // Makes the cookie HTTP-only
                .secure(false) // Use true in production with HTTPS
                .path("/")
                .maxAge(60 * 60) // 1 H in seconds
                .build();

        // Add the cookie to the response
        response.addHeader("Set-Cookie", cookie.toString());

        AuthResponse loginResponse = new AuthResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setMessage("login mrigle");

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup/donor")
    public ResponseEntity<?> registerDonor(@RequestBody Donor donorDto) {
        if (userService.existsByEmail(donorDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé!");
        }

        Donor donor = donorService.createDonor(donorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(donor);
    }

    @PostMapping("/signup/hospial-user")
    public ResponseEntity<?> registerHospialUser(@RequestBody HospitalUser hospitalUserDto) {
        if (userService.existsByEmail(hospitalUserDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé!");
        }

        HospitalUser hospitalUser = hospitalUserService.createHospitalUser(hospitalUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalUser);
    }

    @PostMapping("/disconnect")
    public ResponseEntity<String> disconnect(HttpServletResponse response) {
        // Clear the SecurityContext
        SecurityContextHolder.clearContext();

        // Expire the JWT cookie
        Cookie cookie = new Cookie("authToken", null);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(200)
                .body("User disconnected successfully, cookie expired.");
    }

}
