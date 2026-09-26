package by.bsuir.msa.books.controller;

import by.bsuir.msa.books.dto.BookResponseDto;
import by.bsuir.msa.books.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean BookService bookService;

    @Test
    void listReturnsBooks() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(
                BookResponseDto.builder().id(1L).title("DDD").author("Evans").isbn("1")
                        .yearPublished(2003).createdAt(Instant.now()).updatedAt(Instant.now()).build()
        ));
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("DDD"));
    }
}
