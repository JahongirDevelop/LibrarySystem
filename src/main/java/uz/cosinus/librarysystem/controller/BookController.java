package uz.cosinus.librarysystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.librarysystem.dto.request.BookCr;
import uz.cosinus.librarysystem.dto.request.FloorCr;
import uz.cosinus.librarysystem.dto.response.BookRentWithDeadlineResponse;
import uz.cosinus.librarysystem.dto.response.BookResponse;
import uz.cosinus.librarysystem.enitity.BookEntity;

import uz.cosinus.librarysystem.enitity.enums.RentStatus;
import uz.cosinus.librarysystem.service.BookService;
import uz.cosinus.librarysystem.service.RentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book")
public class BookController {


    private final BookService bookService;
    private final RentService rentService;

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/create-book")
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookCr bookCr) {
        return ResponseEntity.ok(bookService.create(bookCr));
    }
    @GetMapping("/all")
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public BookEntity updateBook(@PathVariable UUID id, @RequestBody BookCr updatedBookCR) {
        return bookService.updateBook(id, updatedBookCR);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchByTitleOrAuthor(@RequestParam String keyWord) {
        return ResponseEntity.status(200).body(bookService.searchByTitleOrAuthor(keyWord));
    }


    @GetMapping("/get-books-rented-by-user{userId}")
    public ResponseEntity<List<BookResponse>> getBooksRentedByUser(@PathVariable UUID userId,
                                                                   @RequestParam RentStatus status){
        return ResponseEntity.status(200).body(rentService.getBooksRentedByUser(userId, status));
    }

    @GetMapping("/get-books-rented-by-deadline{userId}")
    public ResponseEntity<List<BookRentWithDeadlineResponse>> getBooksRentedByDeadline(@PathVariable UUID userId){
        return ResponseEntity.status(200).body(rentService.getBooksRentedByUserWithDeadline(userId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable UUID id) {
        return bookService.getBookById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-book{bookId}")
    public ResponseEntity<String> deleteFloor(@PathVariable UUID bookId){
        return ResponseEntity.status(200).body(bookService.delete(bookId));
    }

}
