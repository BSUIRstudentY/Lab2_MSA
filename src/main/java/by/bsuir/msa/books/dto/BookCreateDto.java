package by.bsuir.msa.books.dto;

import lombok.Data;

@Data
public class BookCreateDto {
    private String title;
    private String author;
    private String isbn;
    private Integer yearPublished;
    private String description;
}
