package ir.nrdc.service.converter;

import ir.nrdc.model.dto.BookDto;
import ir.nrdc.model.entity.Book;
import ir.nrdc.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookDtoConverter {
    @Autowired
    AuthorService authorService;
    @Autowired
    AuthorDtoConverter authorDtoConverter;

    public Book convertDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setSubject(bookDto.getSubject());
        book.setPublisher(bookDto.getPublisher());
        book.setAuthor(authorService.findByNameAndFamily(bookDto.getAuthor()));
        book.setIsbn(bookDto.getIsbn());
        book.setAgeGroup(bookDto.getAgeGroup());
        book.setPublishDate(LocalDate.parse(bookDto.getPublishDate()));
        book.setNumberOfPages(bookDto.getNumberOfPages());
        book.setNumber(bookDto.getNumber());
        return book;
    }

    public BookDto convertBookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setName(book.getName());
        bookDto.setSubject(book.getSubject());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setAuthor(authorDtoConverter.convertAuthorToDto(book.getAuthor()));
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAgeGroup(book.getAgeGroup());
        return bookDto;
    }
}
