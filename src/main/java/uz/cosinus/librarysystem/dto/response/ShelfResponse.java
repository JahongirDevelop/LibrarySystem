package uz.cosinus.librarysystem.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor

@Getter
@Setter
@Builder
public class ShelfResponse {
    private UUID id;
    private Integer number;
    private UUID floorId;

}
