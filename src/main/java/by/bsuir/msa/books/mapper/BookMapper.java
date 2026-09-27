package by.bsuir.msa.books.mapper;

import by.bsuir.msa.books.dto.BookCreateDto;
import by.bsuir.msa.books.dto.BookResponseDto;
import by.bsuir.msa.books.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookCreateDto dto);
    BookResponseDto toDto(Book book);
}
