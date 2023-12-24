package uz.cosinus.librarysystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.config.jwt.AuthDto;
import uz.cosinus.librarysystem.config.jwt.JwtResponse;
import uz.cosinus.librarysystem.config.jwt.JwtService;
import uz.cosinus.librarysystem.dto.request.UserCreateDto;
import uz.cosinus.librarysystem.dto.response.UserResponseDTO;
import uz.cosinus.librarysystem.enitity.BookEntity;
import uz.cosinus.librarysystem.enitity.UserEntity;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;
import uz.cosinus.librarysystem.enitity.enums.UserRole;
import uz.cosinus.librarysystem.exception.DataNotFoundException;
import uz.cosinus.librarysystem.exception.DuplicateValueException;
import uz.cosinus.librarysystem.repository.UserRepository;

import java.security.Principal;
import java.util.*;

import static uz.cosinus.librarysystem.enitity.enums.UserRole.*;

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

    public String signUp(UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("this email is already exists: "+dto.getEmail());
        }
        String verificationCode = senderService.sendVerificationCode(dto.getEmail());
        UserEntity userEntity = modelMapper.map(dto, UserEntity.class);
        userEntity.setPassword(verificationCode);
        userRepository.save(userEntity);
        return "Successfully sign up";
    }



    public  <T> T me(Principal principal) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(principal.getName()))
                .orElseThrow(() -> new DataNotFoundException("User not found!"));;
        UserRole role = userEntity.getUserRole();
        if (role == ADMIN){
            UserResponseDTO adminResponse = modelMapper.map(userEntity,UserResponseDTO.class);
            adminResponse.setRole(ADMIN);
            return (T) adminResponse;
        } else if (role == MODERATOR) {
            UserResponseDTO moderatorResponse =  modelMapper.map(userEntity,UserResponseDTO.class);
            moderatorResponse.setRole(MODERATOR);
            return (T)moderatorResponse;
        }
        return (T) modelMapper.map(userEntity,UserResponseDTO.class);
    }

    public UserResponseDTO updateProfile(UserCreateDto user, Principal principal) {
        UserEntity entity = userRepository.findById(UUID.fromString(principal.getName())).
                orElseThrow(() -> new DataNotFoundException("User not found!"));
        if(!Objects.equals(user.getName(),null)){
            entity.setName(user.getName());
        }if(!Objects.equals(user.getPhoneNumber(),null)){
            entity.setPhoneNumber(user.getPhoneNumber());
        }if(!Objects.equals(user.getPassword(),null)){
            entity.setPassword(user.getPassword());
        }if(!Objects.equals(user.getEmail(),null)){
            entity.setEmail(user.getEmail());
        }
        UserEntity userEntity = userRepository.save(entity);
        return modelMapper.map(userEntity,UserResponseDTO.class);
    }

    public String delete(Principal principal) {
        userRepository.deleteById(UUID.fromString(principal.getName()));
        return "Deleted!";
    }

    public UserResponseDTO addModerator(UserCreateDto userCr) {
        existByEmail(userCr);
        UserEntity userEntity = modelMapper.map(userCr, UserEntity.class);
        userEntity.setUserRole(MODERATOR);
        userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    private void existByEmail(UserCreateDto userCr) {
        if (!userRepository.existsByEmail(userCr.getEmail())) {
            throw new DataNotFoundException("User not found");
        }
    }

    public UserResponseDTO addAdmin(UserCreateDto userCr) {
        if (!userRepository.existsByEmail(userCr.getEmail())) {
            throw new DataNotFoundException("User not found");
        }
        UserEntity userEntity = modelMapper.map(userCr, UserEntity.class);
        userEntity.setUserRole(ADMIN);
        userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserResponseDTO.class);
    }
}
