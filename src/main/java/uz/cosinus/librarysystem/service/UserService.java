package uz.cosinus.librarysystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.config.jwt.AuthDto;
import uz.cosinus.librarysystem.config.jwt.JwtResponse;
import uz.cosinus.librarysystem.config.jwt.JwtService;
import uz.cosinus.librarysystem.dto.request.UserCreateDto;
import uz.cosinus.librarysystem.enitity.UserEntity;
import uz.cosinus.librarysystem.enitity.enums.UserRole;
import uz.cosinus.librarysystem.exception.DataAlreadyExistsException;
import uz.cosinus.librarysystem.exception.DataNotFoundException;
import uz.cosinus.librarysystem.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final EmailSenderService senderService;
    public JwtResponse signIn(AuthDto dto) {
        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new DataNotFoundException("user not found"));
        if (dto.getPassword().equals(user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new AuthenticationCredentialsNotFoundException("password didn't match");
    }

    public String add(UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("this email is already exists");
        }
        String verificationCode = senderService.sendVerificationCode(dto.getEmail());
        UserEntity userEntity = UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(verificationCode)
                .phoneNumber(dto.getPhoneNumber())
                .userRole(UserRole.USER)
                .build();
        userRepository.save(userEntity);
        return "Successfully sign up";
    }
}
