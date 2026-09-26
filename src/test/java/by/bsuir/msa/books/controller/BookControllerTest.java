package by.bsuir.msa.books.controller;

import by.bsuir.msa.books.dto.BookResponseDto;
import by.bsuir.msa.books.exception.GlobalExceptionHandler;
import by.bsuir.msa.books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock BookService bookService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void listReturnsBooks() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(
                BookResponseDto.builder().id(1L).title("DDD").author("Evans").isbn("1")
                        .yearPublished(2003).createdAt(Instant.now()).updatedAt(Instant.now()).build()
        ));
        mockMvc.perform(get("/api/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("DDD"));
    }
}
