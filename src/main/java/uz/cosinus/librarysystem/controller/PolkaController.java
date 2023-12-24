package uz.cosinus.librarysystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.librarysystem.dto.request.PolkaCR;
import uz.cosinus.librarysystem.dto.response.PolkaResponse;
import uz.cosinus.librarysystem.enitity.PolkaEntity;
import uz.cosinus.librarysystem.service.PolkaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/polka")
public class PolkaController {
    private final PolkaService polkaService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<PolkaResponse> createPolka(@RequestBody PolkaCR polkaCR) {
        return ResponseEntity.status(HttpStatus.CREATED).body(polkaService.createPolka(polkaCR));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/all")
    public List<PolkaEntity> getAllPolkas() {
        return polkaService.getAllPolkas();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/{id}")
    public PolkaEntity getPolkaById(@PathVariable UUID id) {
        return polkaService.getPolkaById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public PolkaEntity updatePolka(@PathVariable UUID id, @RequestBody PolkaCR updatedPolkaCR) {
        return polkaService.updatePolka(id, updatedPolkaCR);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePolkaById(@PathVariable UUID id) {
        polkaService.deletePolkaById(id);
        return ResponseEntity.ok("Polka with id " + id + " deleted successfully.");
    }


}
