package uz.cosinus.librarysystem.enitity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.cosinus.librarysystem.enitity.BaseEntity;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RentEntity extends BaseEntity {
    private UUID userId;
    private UUID bookId;
    private LocalDateTime deadline = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private RentStatus status;
}
