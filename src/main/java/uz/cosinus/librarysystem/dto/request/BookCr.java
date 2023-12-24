package uz.cosinus.librarysystem.dto.request;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookCr {
    private String title;
    private String author;
    private Integer pages;
}
