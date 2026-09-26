package by.bsuir.msa.books.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookUpdateDto {
    private String title;
    private String author;
    private String isbn;
    @Positive
    private Integer yearPublished;
    private String description;
}
