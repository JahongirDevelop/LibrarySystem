package uz.cosinus.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.dto.request.RentBookRequest;
import uz.cosinus.librarysystem.dto.response.BookRentWithDeadlineResponse;
import uz.cosinus.librarysystem.dto.response.BookResponse;
import uz.cosinus.librarysystem.dto.response.RentUpdateResponse;
import uz.cosinus.librarysystem.enitity.BookEntity;
import uz.cosinus.librarysystem.enitity.RentEntity;
import uz.cosinus.librarysystem.enitity.enums.RentStatus;
import uz.cosinus.librarysystem.exception.BadRequestException;
import uz.cosinus.librarysystem.exception.DataAlreadyExistsException;
import uz.cosinus.librarysystem.repository.BookRepository;
import uz.cosinus.librarysystem.repository.RentRepository;
import uz.cosinus.librarysystem.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RentRepository rentRepository;
    private final ModelMapper modelMapper;

    public String rentBook(RentBookRequest request) {
        userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
        bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID"));

        boolean isBookRented = rentRepository.findByBookIdAndStatus(request.getBookId(), RentStatus.RENTED)
                .isPresent();
        if (isBookRented) {
            throw new DataAlreadyExistsException("This book is already rented");
        }
        RentEntity rent = modelMapper.map(request, RentEntity.class);
        rent.setStatus(RentStatus.RENTED);
        rent.setDeadline(LocalDateTime.now());
        rentRepository.save(rent);
        return "Successfully rented";
    }

    public RentUpdateResponse takeAway(RentUpdateResponse updateResponse) {
        userRepository.findById(updateResponse.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + updateResponse.getUserId()));
        bookRepository.findById(updateResponse.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + updateResponse.getBookId()));
        Optional<RentEntity> optionalRent = rentRepository.findByBookIdAndStatus(updateResponse.getBookId(), RentStatus.RENTED);

        if (optionalRent.isPresent()) {
            RentEntity rentEntity = optionalRent.get();
            LocalDateTime rentDate = rentEntity.getDeadline();
            LocalDateTime currentDate = LocalDateTime.now();
            Duration duration = Duration.between(rentDate, currentDate);
            long daysDifference = duration.toDays();
            if (daysDifference < 3) {
                rentEntity.setStatus(RentStatus.TAKE_AWAY);
                rentRepository.save(rentEntity);
                return modelMapper.map(rentEntity, RentUpdateResponse.class);
            } else {
                rentRepository.delete(rentEntity);
                throw new BadRequestException("The deadline for this book is 3 days");
            }
        } else {
            throw new EntityNotFoundException("No rented book found with ID: " + updateResponse.getBookId());
        }
    }

    public List<BookResponse> getBooksRentedByUser(UUID userId, RentStatus status) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        List<RentEntity> rentedBooks = rentRepository.findAllByUserIdAndStatus(userId, status);
//        RentEntity rentEntity = new RentEntity();
//        BookEntity bookEntity = bookRepository.findById(rentEntity.getBookId()).get();

        List<BookResponse> collect = rentedBooks.stream()
                .map(rentEntity -> modelMapper.map(bookRepository.findById(rentEntity.getBookId()).get(), BookResponse.class))
                .toList();

        List<BookResponse> list = new ArrayList<>();
        for (BookResponse bookResponse : collect) {
            bookResponse.setStatus(status);
            list.add(bookResponse);
        }
        return list;
    }

    public List<BookRentWithDeadlineResponse> getBooksRentedByUserWithDeadline(UUID userId) {
        List<RentEntity> rentedBooks = rentRepository.findAllByUserId(userId);
        return rentedBooks.stream()
                .map(rentEntity -> {
                    UUID bookId = rentEntity.getBookId();
                    BookEntity bookEntity = bookRepository.findById(bookId).get();
                    BookRentWithDeadlineResponse response = modelMapper.map(rentEntity, BookRentWithDeadlineResponse.class);
                    response.setTitle(bookEntity.getTitle());
                    response.setAuthor(bookEntity.getAuthor());
                    response.setDeadline(rentEntity.getDeadline());
                    response.setRentStatus(rentEntity.getStatus());
                    return response;
                })
                .collect(Collectors.toList());
    }
}

