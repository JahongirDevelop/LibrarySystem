package uz.cosinus.librarysystem.dto.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor

@Getter
@Setter
@Builder
public class ShelfCr {
    private Integer number;
    private UUID floorId;

}
