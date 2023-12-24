package uz.cosinus.librarysystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.librarysystem.dto.request.ShelfCr;
import uz.cosinus.librarysystem.dto.response.ShelfResponse;
import uz.cosinus.librarysystem.enitity.ShelfEntity;
import uz.cosinus.librarysystem.service.ShelfService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shelf")
public class ShelfController {
    private final ShelfService shelfService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-shkaf")
    public ResponseEntity<ShelfResponse> createShkaf(@RequestBody @Valid ShelfCr shelfCr) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shelfService.create(shelfCr));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-shkaf{shkafId}")
    public ResponseEntity<String> deleteShkaf(@PathVariable UUID shkafId){
        return ResponseEntity.status(200).body(shelfService.delete(shkafId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/all")
    public List<ShelfEntity> getAllShelf() {
        return shelfService.getAllShelf();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/{id}")
    public ShelfEntity getShelfById(@PathVariable UUID id) {
        return shelfService.getShelfById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ShelfEntity updateShelf(@PathVariable UUID id, @RequestBody ShelfCr updatedShelfCR) {
        return shelfService.updateShelf(id, updatedShelfCR);
    }
}
