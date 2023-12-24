package uz.cosinus.librarysystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.librarysystem.dto.request.RentBookRequest;
import uz.cosinus.librarysystem.dto.response.RentUpdateResponse;
import uz.cosinus.librarysystem.service.RentService;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rent")
public class RentController {
    private final RentService rentService;

    @PostMapping("/rent-book")
    public ResponseEntity<String> rentBook(@RequestBody RentBookRequest request){
        return ResponseEntity.status(200).body(rentService.rentBook(request));
    }

    @PutMapping("/take-away")
    public ResponseEntity<RentUpdateResponse> takeAway(@RequestBody RentUpdateResponse updateResponse){
        return ResponseEntity.status(200).body(rentService.takeAway(updateResponse));
    }
}
