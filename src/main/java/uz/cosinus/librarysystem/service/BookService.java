package uz.cosinus.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.dto.request.BookCr;
import uz.cosinus.librarysystem.dto.response.BookResponse;
import uz.cosinus.librarysystem.enitity.BookEntity;
import uz.cosinus.librarysystem.enitity.FloorEntity;
import uz.cosinus.librarysystem.enitity.RentEntity;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;
import uz.cosinus.librarysystem.exception.DataNotFoundException;
import uz.cosinus.librarysystem.exception.DuplicateValueException;
import uz.cosinus.librarysystem.repository.BookRepository;
import uz.cosinus.librarysystem.repository.RentRepository;
import uz.cosinus.librarysystem.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RentRepository repository;
    private final ModelMapper modelMapper;
    public BookResponse create(BookCr bookCr) {
        if (bookRepository.existsByTitle(bookCr.getTitle())) {
            throw new DuplicateValueException("This book already exists with title '" + bookCr.getTitle() + "'");}
        BookResponse bookResponse = modelMapper.map(bookCr, BookResponse.class);
        bookResponse.setStatus(RentStatus.HERE);
        bookRepository.save(modelMapper.map(bookResponse, BookEntity.class));
        return bookResponse;
    }

    public List<BookResponse> searchByTitleOrAuthor(String keyWord) {
        List<BookEntity> bookEntities = bookRepository.searchAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyWord, keyWord);
//        return modelMapper.map(bookEntities, BookResponse.class);
        return bookEntities.stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookResponse.class))
                .collect(Collectors.toList());
    }
    public BookEntity getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    public BookEntity updateBook(UUID id, BookCr updatedBookCR) {
        BookEntity existingBook = getBookById(id);
        modelMapper.map(updatedBookCR, existingBook);
        return existingBook;
    }
    public BookEntity getBook(UUID bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found!"));
    }

    public String delete(UUID bookId){
        BookEntity book = getBook(bookId);
        bookRepository.deleteById(book.getId());
        return "Successfully deleted";
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }
}
