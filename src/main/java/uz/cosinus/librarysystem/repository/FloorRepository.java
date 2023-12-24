package uz.cosinus.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.librarysystem.enitity.FloorEntity;

import java.util.Optional;
import java.util.UUID;

public interface FloorRepository extends JpaRepository<FloorEntity, UUID> {
    boolean existsFloorEntityByNumber(Integer number);

}
