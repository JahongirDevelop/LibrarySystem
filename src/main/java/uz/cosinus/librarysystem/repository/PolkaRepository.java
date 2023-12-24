package uz.cosinus.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.librarysystem.enitity.PolkaEntity;

import java.util.UUID;

public interface PolkaRepository extends JpaRepository<PolkaEntity, UUID> {
}
