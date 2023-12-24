package uz.cosinus.librarysystem.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PolkaResponse {
    private UUID Id;
    private Integer number;
    private Integer bookCount;
}
