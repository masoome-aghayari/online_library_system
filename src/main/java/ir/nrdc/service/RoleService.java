package ir.nrdc.service;

import ir.nrdc.model.entity.Role;
import ir.nrdc.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }

    @Transactional
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }
}
