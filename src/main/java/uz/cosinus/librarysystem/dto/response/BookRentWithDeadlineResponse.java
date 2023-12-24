package uz.cosinus.librarysystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRentWithDeadlineResponse {
    private UUID bookId;
    private String title;
    private String author;
    private RentStatus rentStatus;
    private LocalDateTime deadline = LocalDateTime.now();
}
