package ir.nrdc.model.repository;

import ir.nrdc.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, Integer>, JpaSpecificationExecutor<User> {
    void save(User user);

    Optional<User> findByNationalId(String nationalId);

    long countByNationalId(String nationalId);

}