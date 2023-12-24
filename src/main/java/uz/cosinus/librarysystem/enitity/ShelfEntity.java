package uz.cosinus.librarysystem.enitity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "shkaf_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShelfEntity extends BaseEntity {

    @Column(unique = true)
    private Integer number;

    @JoinColumn(name = "floor_id")
    @ManyToOne
    private FloorEntity floor;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private List<PolkaEntity> polka;
}

