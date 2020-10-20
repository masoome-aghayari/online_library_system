package ir.nrdc.service.converter;

import ir.nrdc.model.dto.BookDto;
import ir.nrdc.model.entity.Book;
import ir.nrdc.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        bookDto.setNumberOfPages(book.getNumberOfPages());
        bookDto.setNumber(book.getNumber());
        return bookDto;
        //bookDto.setPublishDate(book.getPublishDate());
    }

    public ArrayList<BookDto> convertBookListToDtoList(List<Book> bookList) {
        return bookList.stream().map(this::convertBookToDto).collect(Collectors.toCollection(ArrayList::new));
    }

    public Page<BookDto> convertBookPageToDtoPage(Page<Book> bookPage) {
        return new PageImpl<>(convertBookListToDtoList(bookPage.getContent()));
    }
}
