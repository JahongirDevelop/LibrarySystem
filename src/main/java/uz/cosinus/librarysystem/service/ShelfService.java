package uz.cosinus.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.dto.request.FloorCr;
import uz.cosinus.librarysystem.dto.request.ShelfCr;
import uz.cosinus.librarysystem.dto.response.ShelfResponse;
import uz.cosinus.librarysystem.enitity.FloorEntity;
import uz.cosinus.librarysystem.enitity.ShelfEntity;
import uz.cosinus.librarysystem.exception.DataNotFoundException;
import uz.cosinus.librarysystem.repository.FloorRepository;
import uz.cosinus.librarysystem.repository.ShelfRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShelfService {
    private final ShelfRepository shelfRepository;
    private final FloorRepository floorRepository;

    public ShelfResponse create(ShelfCr shelfCr) {
        ShelfEntity shelfEntity = new ShelfEntity();
        shelfEntity.setNumber(shelfCr.getNumber());
        FloorEntity floor = floorRepository.findById(shelfCr.getFloorId())
                .orElseThrow(() -> new EntityNotFoundException("Floor with id " + shelfCr.getFloorId() + " not found."));
        shelfEntity.setFloor(floor);

        ShelfEntity savedShelf = shelfRepository.save(shelfEntity);
        return mapToShelfResponse(savedShelf);

    }

    private ShelfResponse mapToShelfResponse(ShelfEntity shelfEntity) {
        ShelfResponse shelfResponse = ShelfResponse.builder()
                .id(shelfEntity.getId())
                .number(shelfEntity.getNumber())
                .floorId(shelfEntity.getFloor().getId())
                .build();
        return shelfResponse;
    }


    public String delete(UUID shelfId){
        ShelfEntity shelfEntity = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new EntityNotFoundException("Shelf with id " + shelfId + " not found."));
        shelfRepository.delete(shelfEntity);
        return "successfully deleted";
    }
    public List<ShelfEntity> getAllShelf() {
        return shelfRepository.findAll();
    }

    public ShelfEntity getShelf(UUID shelfId){
        return shelfRepository.findById(shelfId)
                .orElseThrow(() -> new DataNotFoundException("Shelf not found!"));
    }

    public ShelfEntity getShelfById(UUID id) {
        return shelfRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shelf not found with id: " + id));
    }

    public ShelfEntity updateShelf(UUID id, ShelfCr updatedShelfCR) {
        ShelfEntity existingShelf = getShelfById(id);
        existingShelf.setNumber(updatedShelfCR.getNumber());
        return shelfRepository.save(existingShelf);
    }
}
