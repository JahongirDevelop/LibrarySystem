package uz.cosinus.librarysystem.dto.request;

import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PolkaCR {
    private UUID shelfId;
    private Integer number;
    private Integer bookCount;
}
