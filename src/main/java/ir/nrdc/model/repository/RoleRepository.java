package ir.nrdc.model.repository;

import ir.nrdc.model.entity.Role;
import ir.nrdc.model.entity.RoleName;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface RoleRepository extends Repository<Role, Integer>, JpaSpecificationExecutor<Integer> {
    void save(Role role);

    List<Role> findAll();

    @Query("select role.roleName From Role role where not role.roleName ='admin'")
    List<String> findUserRoles();

    Optional<Role> findByRoleName(RoleName roleName);
}
