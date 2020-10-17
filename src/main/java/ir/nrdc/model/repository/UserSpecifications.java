package ir.nrdc.model.repository;

import com.mysql.cj.util.StringUtils;
import ir.nrdc.model.entity.Role;
import ir.nrdc.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSpecifications {
    public static Specification<User> findMaxMatch(String firstName,
                                                   String lastName,
                                                   String nationalId) {
        return (Specification<User>) (root, criteriaQuery, builder) -> {
            CriteriaQuery<User> resultCriteria = builder.createQuery(User.class);

            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isNullOrEmpty(firstName)) {
                predicates.add(builder.equal(root.get("firstName"), firstName));
            }
            if (!StringUtils.isNullOrEmpty(lastName)) {
                predicates.add(builder.equal(root.get("lastName"), lastName));
            }
            if (!StringUtils.isNullOrEmpty(nationalId)) {
                predicates.add(builder.equal(root.get("nationalId"), nationalId));
            }
            resultCriteria.select(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }
}
