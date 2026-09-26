package by.bsuir.msa.books.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class BookResponseDto {
    Long id;
    String title;
    String author;
    String isbn;
    Integer yearPublished;
    String description;
    Instant createdAt;
    Instant updatedAt;
}
