package uz.cosinus.librarysystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.cosinus.librarysystem.config.jwt.AuthDto;
import uz.cosinus.librarysystem.config.jwt.JwtResponse;
import uz.cosinus.librarysystem.dto.request.UserCreateDto;
import uz.cosinus.librarysystem.service.UserService;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public JwtResponse signIn(@Valid @RequestBody AuthDto dto) {
        return userService.signIn(dto);
    }

    @PostMapping("/sign-up")
    public String auth(@Valid @RequestBody UserCreateDto dto) {
        return userService.signUp(dto);
    }
}
