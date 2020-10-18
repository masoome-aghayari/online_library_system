package ir.nrdc.model.repository;

import ir.nrdc.model.entity.Book;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface BookRepository extends Repository<Book, Integer>, JpaSpecificationExecutor<Integer> {
    void save(Book book);

    List<Book> findByName(String name);

    List<Book> findByAuthor(String author);// TODO: criteria

    void deleteByName(String name);

    void deleteByIsbn(String isbn);

    void deleteByAuthor(String author); //TODO: criteria

    Optional<Book> findByIsbn(String isbn);

    @Modifying
    @Query("update Book b set  b.number= :number where b.isbn= :isbn")
    void updateNumberOfBooks(@Param("number") int number, @Param("isbn") String isbn);

    @Query("select b.number from Book  b where b.isbn= :isbn")
    int findNumberOfBook(@Param("isbn") String isbn);
}
