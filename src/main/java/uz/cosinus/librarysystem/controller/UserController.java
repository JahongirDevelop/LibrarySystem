package uz.cosinus.librarysystem.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.librarysystem.config.jwt.AuthDto;
import uz.cosinus.librarysystem.config.jwt.JwtResponse;
import uz.cosinus.librarysystem.dto.request.RentBookRequest;
import uz.cosinus.librarysystem.dto.request.UserCreateDto;
import uz.cosinus.librarysystem.dto.response.RentUpdateResponse;
import uz.cosinus.librarysystem.service.RentService;
import uz.cosinus.librarysystem.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final RentService rentBookService;

    @PostMapping("/rent-book")
    public ResponseEntity<String> rentBook(@RequestBody RentBookRequest request){
        return ResponseEntity.status(200).body(rentBookService.rentBook(request));
    }

    @PutMapping("/take-away")
    public ResponseEntity<RentUpdateResponse> takeAway(@RequestBody RentUpdateResponse updateResponse){
        return ResponseEntity.status(200).body(rentBookService.takeAway(updateResponse));
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@Valid @RequestBody AuthDto dto) {
        return userService.signIn(dto);
    }

    @PostMapping("/sign-up")
    public String auth(@Valid @RequestBody UserCreateDto dto) {
        return userService.add(dto);
    }
}
