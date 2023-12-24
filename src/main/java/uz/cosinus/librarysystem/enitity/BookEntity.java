package uz.cosinus.librarysystem.enitity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookEntity extends BaseEntity{
    private String title;
    private String author;
    private Integer pages;

}
