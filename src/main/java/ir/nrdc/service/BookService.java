package ir.nrdc.service;

import ir.nrdc.model.dto.BookDto;
import ir.nrdc.model.entity.Author;
import ir.nrdc.model.entity.Book;
import ir.nrdc.model.repository.BookRepository;
import ir.nrdc.model.repository.BookSpecifications;
import ir.nrdc.service.converter.BookDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:constant_numbers.properties")
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookDtoConverter bookDtoConverter;
    @Autowired
    AuthorService authorService;
    @Autowired
    Environment env;

    @Transactional
    public void addNewBook(BookDto bookDto) {
        if (isExists(bookDto.getIsbn()))
            updateNumberOfBook(bookDto.getIsbn(), bookDto.getNumber());
        else {
            Book newBook = bookDtoConverter.convertDtoToBook(bookDto);
            if (!authorService.isExists(bookDto.getAuthor()))
                newBook.setAuthor(authorService.addNewAuthor(bookDto.getAuthor()));
            bookRepository.save(newBook);
        }
    }

    @Transactional
    public boolean isExists(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }

    @Transactional
    public void updateNumberOfBook(String isbn, int number) {
        number += bookRepository.findNumberOfBook(isbn);
        bookRepository.updateNumberOfBooks(number, isbn);
    }

    @Transactional
    public int getTotalNumberOfPages(BookDto bookDto) {
        Author author = getAuthor(bookDto);
        long totalMatched = bookRepository.count(BookSpecifications.findMaxMatch(bookDto.getName(), bookDto.getIsbn(),
                author));
        int rowsNumberInPage = Integer.parseInt(env.getProperty("Page.Rows"));
        double pages = (double) totalMatched / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }


    @Transactional
    public List<BookDto> findMaxMatch(BookDto bookDto, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "name", "subject");
        Author author = getAuthor(bookDto);
        Page<Book> matchedBooks = bookRepository.findAll(BookSpecifications.findMaxMatch(bookDto.getName(),
                bookDto.getIsbn(), author), pageable);
        return bookDtoConverter.convertBookPageToDtoPage(matchedBooks).getContent();
    }

    private Author getAuthor(BookDto bookDto) {
        Author author = new Author();
        if (Objects.isNull(bookDto.getAuthor()))
            author = null;
        else {
            author.setName(bookDto.getAuthor().getName());
            author.setFamily(bookDto.getAuthor().getFamily());
        }
        return author;
    }

    @Transactional
    public void deleteBooks(List<BookDto> bookDtos) {
        for (BookDto bookDto : bookDtos) {
            bookRepository.deleteByIsbn(bookDto.getIsbn());
        }
    }
}
