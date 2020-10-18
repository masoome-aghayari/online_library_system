package ir.nrdc.service;

import ir.nrdc.model.dto.BookDto;
import ir.nrdc.model.entity.Book;
import ir.nrdc.model.repository.BookRepository;
import ir.nrdc.service.converter.BookDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookDtoConverter bookDtoConverter;
    @Autowired
    AuthorService authorService;

    @Transactional
    public void addNewBook(BookDto bookDto) {
        if (isExists(bookDto.getIsbn()))
            updateNumberOfBook(bookDto.getIsbn(), bookDto.getNumber());
        else {
            Book newBook = bookDtoConverter.convertDtoToBook(bookDto);
            if (!authorService.isExists(bookDto.getAuthor()))
                authorService.addNewAuthor(bookDto.getAuthor());
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
}
