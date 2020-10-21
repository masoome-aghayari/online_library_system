package ir.nrdc.model.repository;

import ir.nrdc.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, Integer>, JpaSpecificationExecutor<User> {
    void save(User user);

    Optional<User> findByNationalId(String nationalId);

    long countByNationalId(String nationalId);

    void deleteByNationalId(String nationalId);

    @Modifying
    @Query(value = "update User u set u.firstName= :firstName, u.lastName= :lastName, u.nationalId= :nationalId," +
            " u.mobileNumber= :mobileNumber, u.email= :email where u.id= :id")
    void updateMember(@Param("id") int id, @Param("firstName") String firstName, @Param("lastName") String lastName,
                      @Param("nationalId") String nationalId, @Param("mobileNumber") String mobileNumber,
                      @Param("email") String email);
}