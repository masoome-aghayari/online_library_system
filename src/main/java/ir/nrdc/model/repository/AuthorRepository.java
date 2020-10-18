package ir.nrdc.model.repository;

import ir.nrdc.model.entity.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface AuthorRepository extends Repository<Author, Integer>, JpaSpecificationExecutor<Integer> {
    void save(Author author);

    Optional<Author> findByNameAndFamily(@Param("name") String name, @Param("family") String family);
}
