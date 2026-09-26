package by.bsuir.msa.books.service;

import by.bsuir.msa.books.dto.BookCreateDto;
import by.bsuir.msa.books.dto.BookResponseDto;
import by.bsuir.msa.books.exception.ConflictException;
import by.bsuir.msa.books.mapper.BookMapper;
import by.bsuir.msa.books.model.Book;
import by.bsuir.msa.books.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock BookRepository bookRepository;
    @Mock BookMapper bookMapper;
    @InjectMocks BookService bookService;

    @Test
    void createThrowsWhenIsbnExists() {
        BookCreateDto dto = new BookCreateDto();
        dto.setIsbn("111");
        when(bookRepository.existsByIsbn("111")).thenReturn(true);
        assertThrows(ConflictException.class, () -> bookService.create(dto));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void createPersistsWhenIsbnFree() {
        BookCreateDto dto = new BookCreateDto();
        dto.setTitle("T");
        dto.setAuthor("A");
        dto.setIsbn("222");
        dto.setYearPublished(2020);
        Book entity = Book.builder().title("T").author("A").isbn("222").yearPublished(2020).build();
        Book saved = Book.builder().id(1L).title("T").author("A").isbn("222").yearPublished(2020)
                .createdAt(Instant.now()).updatedAt(Instant.now()).build();
        when(bookRepository.existsByIsbn("222")).thenReturn(false);
        when(bookMapper.toEntity(dto)).thenReturn(entity);
        when(bookRepository.save(entity)).thenReturn(saved);
        when(bookMapper.toResponse(saved)).thenReturn(
                BookResponseDto.builder().id(1L).title("T").author("A").isbn("222")
                        .yearPublished(2020).createdAt(saved.getCreatedAt()).updatedAt(saved.getUpdatedAt()).build());
        assertEquals(1L, bookService.create(dto).getId());
        verify(bookRepository).save(entity);
    }
}
