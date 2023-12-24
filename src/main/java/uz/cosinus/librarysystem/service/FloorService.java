package uz.cosinus.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.dto.request.FloorCr;
import uz.cosinus.librarysystem.dto.response.FloorResponse;
import uz.cosinus.librarysystem.enitity.FloorEntity;
import uz.cosinus.librarysystem.exception.DataNotFoundException;
import uz.cosinus.librarysystem.exception.DuplicateValueException;
import uz.cosinus.librarysystem.repository.FloorRepository;
import uz.cosinus.librarysystem.repository.PolkaRepository;
import uz.cosinus.librarysystem.repository.ShelfRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final FloorRepository floorRepository;
    private final ShelfRepository shelfRepository;
    private final PolkaRepository polkaRepository;
    private final ModelMapper modelMapper;

    public FloorResponse create(FloorCr floorCr) {
       if (floorRepository.existsFloorEntityByNumber(floorCr.getNumber())){
           throw new DuplicateValueException("Floor already with number!");
       }

        FloorEntity build = FloorEntity.builder()
                .number(floorCr.getNumber()).build();
        floorRepository.save(build);

        return modelMapper.map(build, FloorResponse.class);
    }

    public String delete(UUID floorId){
        FloorEntity floor = getFloor(floorId);
        floorRepository.deleteById(floor.getId());
        return "Successfully deleted";
    }

    public FloorEntity getFloor(UUID floorId){
        return floorRepository.findById(floorId)
                .orElseThrow(() -> new DataNotFoundException("Floor not found!"));
    }

    public List<FloorEntity> getAllFloors() {
        return floorRepository.findAll();
    }

    public FloorEntity getFloorById(UUID id) {
        return floorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Floor not found with id: " + id));
    }

    public FloorEntity updateFloor(UUID id, FloorCr updatedFloorCR) {
        FloorEntity existingFloor = getFloorById(id);
        existingFloor.setNumber(updatedFloorCR.getNumber());
        return floorRepository.save(existingFloor);
    }
}
