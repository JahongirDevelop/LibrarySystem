package uz.cosinus.librarysystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {
    private String title;
    private String author;
    private Integer pages;
    private RentStatus status;
}
