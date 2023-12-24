package uz.cosinus.librarysystem.enitity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity(name = "floor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FloorEntity extends BaseEntity {
    @Column(unique = true)
    private Integer number;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    private List<ShelfEntity> shelf; // shkaf

}
