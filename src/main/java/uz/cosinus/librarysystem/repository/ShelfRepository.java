package uz.cosinus.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.librarysystem.enitity.ShelfEntity;

import java.util.UUID;

public interface ShelfRepository extends JpaRepository<ShelfEntity, UUID> {
}
