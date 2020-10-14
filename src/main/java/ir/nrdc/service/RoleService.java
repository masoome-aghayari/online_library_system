package ir.nrdc.service;

import ir.nrdc.model.entity.Role;
import ir.nrdc.model.entity.RoleName;
import ir.nrdc.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findRoleByName(String roleName) {
        return roleRepository.findByRoleName(RoleName.valueOf(roleName)).orElse(null);
    }
}
