package by.bsuir.msa.books.service;

import by.bsuir.msa.books.dto.BookCreateDto;
import by.bsuir.msa.books.dto.BookResponseDto;
import by.bsuir.msa.books.mapper.BookMapper;
import by.bsuir.msa.books.model.Book;
import by.bsuir.msa.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookResponseDto create(BookCreateDto dto) {
        if (bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new RuntimeException("isbn already exists");
        }
        Book book = bookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }

    public List<BookResponseDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("book not found"));
        return bookMapper.toDto(book);
    }

    public BookResponseDto update(Long id, BookCreateDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("book not found"));
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setYearPublished(dto.getYearPublished());
        book.setDescription(dto.getDescription());
        return bookMapper.toDto(bookRepository.save(book));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
