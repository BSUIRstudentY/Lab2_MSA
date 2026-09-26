package by.bsuir.msa.books.service;

import by.bsuir.msa.books.dto.BookCreateDto;
import by.bsuir.msa.books.dto.BookResponseDto;
import by.bsuir.msa.books.dto.BookUpdateDto;
import by.bsuir.msa.books.exception.ConflictException;
import by.bsuir.msa.books.exception.ResourceNotFoundException;
import by.bsuir.msa.books.mapper.BookMapper;
import by.bsuir.msa.books.model.Book;
import by.bsuir.msa.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookResponseDto create(BookCreateDto dto) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new ConflictException("Book with ISBN already exists: " + dto.getIsbn());
        }
        Book saved = bookRepository.save(bookMapper.toEntity(dto));
        return bookMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BookResponseDto findById(Long id) {
        return bookMapper.toResponse(getOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<BookResponseDto> findByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCase(author).stream().map(bookMapper::toResponse).toList();
    }

    public BookResponseDto update(Long id, BookUpdateDto dto) {
        Book book = getOrThrow(id);
        if (dto.getIsbn() != null && !dto.getIsbn().equals(book.getIsbn())
                && bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new ConflictException("Book with ISBN already exists: " + dto.getIsbn());
        }
        bookMapper.updateEntity(dto, book);
        return bookMapper.toResponse(book);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    private Book getOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
    }
}
