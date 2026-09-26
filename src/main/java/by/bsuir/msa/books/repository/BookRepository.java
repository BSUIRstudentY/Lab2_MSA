package by.bsuir.msa.books.repository;

import by.bsuir.msa.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthorIgnoreCase(String author);
    boolean existsByIsbn(String isbn);
}
