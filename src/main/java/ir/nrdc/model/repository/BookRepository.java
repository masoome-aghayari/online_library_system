package ir.nrdc.model.repository;

import ir.nrdc.model.entity.Book;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface BookRepository extends Repository<Book, Integer>, JpaSpecificationExecutor<Integer> {
    void save(Book book);

    List<Book> findByName(String name);

    List<Book> findByAuthor(String author);// TODO: criteria

    List<Book> findByIsbn(String isbn);

    void deleteByName(String name);

    void deleteByIsbn(String isbn);

    void deleteByAuthor(String author); //TODO: criteria

}
