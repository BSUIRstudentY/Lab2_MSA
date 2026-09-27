package by.bsuir.msa.books.service;

import by.bsuir.msa.books.dto.BookCreateDto;
import by.bsuir.msa.books.dto.BookResponseDto;
import by.bsuir.msa.books.mapper.BookMapper;
import by.bsuir.msa.books.model.Book;
import by.bsuir.msa.books.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    BookMapper bookMapper;
    @InjectMocks
    BookService bookService;

    @Test
    void createBook() {
        BookCreateDto dto = new BookCreateDto();
        dto.setTitle("Test");
        dto.setAuthor("Author");
        dto.setIsbn("123");

        Book entity = new Book();
        entity.setTitle("Test");

        Book saved = new Book();
        saved.setId(1L);
        saved.setTitle("Test");

        BookResponseDto response = new BookResponseDto();
        response.setId(1L);
        response.setTitle("Test");

        when(bookMapper.toEntity(dto)).thenReturn(entity);
        when(bookRepository.save(any())).thenReturn(saved);
        when(bookMapper.toDto(saved)).thenReturn(response);

        BookResponseDto result = bookService.create(dto);
        assertEquals(1L, result.getId());
    }

    // TODO: тест на update
}
