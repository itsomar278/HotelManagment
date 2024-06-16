package Omar.HotelWebServer.controller;

import Omar.HotelWebServer.dataAccess.model.classes.User;
import Omar.HotelWebServer.dataAccess.model.enums.UserRoles;
import Omar.HotelWebServer.dataAccess.repository.UserRepository;
import Omar.HotelWebServer.security.TokenProvider;
import Omar.HotelWebServer.service.MyUserDetailsService;
import Omar.HotelWebServer.service.UserService;
import Omar.HotelWebServer.utils.DTOs.AuthResponseDTO;
import Omar.HotelWebServer.utils.DTOs.LoginDTO;
import Omar.HotelWebServer.utils.DTOs.RegisterDTO;
import Omar.HotelWebServer.utils.DTOs.UpdatePasswordDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;
    private UserService userService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.provideToken(authentication);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(UserRoles.ROLE_USER);
        userService.createUser(user);

        return ResponseEntity.ok("User registered successfully");
    }


    @PutMapping("/password/{userId}")
    public ResponseEntity<String> changeUserPassword(
            @PathVariable Integer userId,
            @RequestBody UpdatePasswordDTO dto) {
        userService.updateUserPassword(dto, userId);
        return ResponseEntity.ok("User password updated successfully");
    }


}
