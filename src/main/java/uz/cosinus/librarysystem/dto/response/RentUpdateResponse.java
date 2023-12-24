package uz.cosinus.librarysystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentUpdateResponse {
    private UUID userId;
    private UUID bookId;

}
