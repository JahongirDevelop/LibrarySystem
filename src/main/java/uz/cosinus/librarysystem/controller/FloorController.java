package uz.cosinus.librarysystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.librarysystem.dto.request.FloorCr;

import uz.cosinus.librarysystem.dto.response.FloorResponse;
import uz.cosinus.librarysystem.enitity.FloorEntity;
import uz.cosinus.librarysystem.enitity.FloorEntity;
import uz.cosinus.librarysystem.enitity.PolkaEntity;
import uz.cosinus.librarysystem.service.FloorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/floor")
public class FloorController {
    private final FloorService floorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-floor")
    public ResponseEntity<FloorResponse> createFloor(@RequestBody @Valid FloorCr floorCr) {
        return ResponseEntity.status(HttpStatus.CREATED).body(floorService.create(floorCr));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-floor{floorId}")
    public ResponseEntity<String> deleteFloor(@PathVariable UUID floorId){
        return ResponseEntity.status(200).body(floorService.delete(floorId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/all")
    public List<FloorEntity> getAllFloors() {
        return floorService.getAllFloors();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/{id}")
    public FloorEntity getFloorById(@PathVariable UUID id) {
        return floorService.getFloorById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public FloorEntity updateFloor(@PathVariable UUID id, @RequestBody FloorCr updatedFloorCR) {
        return floorService.updateFloor(id, updatedFloorCR);
    }
}
