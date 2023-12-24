package uz.cosinus.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cosinus.librarysystem.enitity.BookEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    boolean existsByTitle(String title);
    List<BookEntity> searchAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);


}
