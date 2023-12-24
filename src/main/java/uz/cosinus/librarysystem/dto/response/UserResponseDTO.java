package uz.cosinus.librarysystem.dto.response;

import lombok.*;
import uz.cosinus.librarysystem.enitity.enums.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponseDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
}
