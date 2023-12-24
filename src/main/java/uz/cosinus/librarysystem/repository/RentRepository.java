package uz.cosinus.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cosinus.librarysystem.enitity.BookEntity;
import uz.cosinus.librarysystem.enitity.RentEntity;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, UUID> {
    Optional<RentEntity> findByBookIdAndStatus(UUID bookId, RentStatus rentStatus);

    List<RentEntity> findAllByUserId(UUID userId);

    List<RentEntity> findAllByUserIdAndStatus(UUID userId, RentStatus status);

    List<RentEntity> findAllByBookId(UUID bookId);


}
