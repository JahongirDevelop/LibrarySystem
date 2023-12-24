package uz.cosinus.librarysystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.librarysystem.config.jwt.AuthDto;
import uz.cosinus.librarysystem.config.jwt.JwtResponse;
import uz.cosinus.librarysystem.dto.request.RentBookRequest;
import uz.cosinus.librarysystem.dto.request.UserCreateDto;
import uz.cosinus.librarysystem.dto.response.UserResponseDTO;
import uz.cosinus.librarysystem.dto.response.RentUpdateResponse;
import uz.cosinus.librarysystem.service.RentService;
import uz.cosinus.librarysystem.service.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserCreateDto userCreateDto, Principal principal){
        return ResponseEntity.ok(userService.updateProfile(userCreateDto, principal));
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(Principal principal){
        return ResponseEntity.ok(userService.me(principal));
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<String > delete(Principal principal){
        return ResponseEntity.status(200).body(userService.delete(principal));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<UserResponseDTO> createAdmin(@RequestBody @Valid UserCreateDto userCr) {
        return ResponseEntity.ok(userService.addAdmin(userCr));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-moderator")
    public ResponseEntity<UserResponseDTO> createModerator(@RequestBody @Valid UserCreateDto userCr) {
        return ResponseEntity.ok(userService.addModerator(userCr));
    }


}
