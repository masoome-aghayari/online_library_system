package ir.nrdc.model.repository;

import com.mysql.cj.util.StringUtils;
import ir.nrdc.model.entity.Author;
import ir.nrdc.model.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookSpecifications {
    public static Specification<Book> findMaxMatch(String name,
                                                   String isbn,
                                                   Author author) {
        return (Specification<Book>) (root, criteriaQuery, builder) -> {
            CriteriaQuery<Book> resultCriteria = builder.createQuery(Book.class);
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(author)) {
                Join<Object, Object> bookJoin = root.join("author");
                if (!StringUtils.isNullOrEmpty(author.getName())) {
                    predicates.add(builder.equal(bookJoin.get("name"), author.getName()));
                }
                if (!StringUtils.isNullOrEmpty(author.getFamily())) {
                    predicates.add(builder.equal(bookJoin.get("family"), author.getFamily()));
                }
            }
            if (!StringUtils.isNullOrEmpty(name)) {
                predicates.add(builder.equal(root.get("name"), name));
            }

            if (!StringUtils.isNullOrEmpty(isbn)) {
                predicates.add(builder.equal(root.get("isbn"), isbn));
            }
            resultCriteria.select(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }
}
