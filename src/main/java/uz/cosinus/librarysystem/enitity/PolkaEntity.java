package uz.cosinus.librarysystem.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PolkaEntity extends BaseEntity {
    @ManyToOne
    private ShelfEntity shelf;

    @Column(unique = true)
    private Integer number;
    @Max(value = 20, message = "each shelf can hold a maximum of 20 books")
    private Integer bookCount;

}
