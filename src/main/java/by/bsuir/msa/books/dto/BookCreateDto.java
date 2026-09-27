package by.bsuir.msa.books.dto;

import lombok.Data;

// dto для создания книги
@Data
public class BookCreateDto {
    private String title;
    private String author;
    private String isbn;
    private Integer yearPublished;
    private String description;
}
