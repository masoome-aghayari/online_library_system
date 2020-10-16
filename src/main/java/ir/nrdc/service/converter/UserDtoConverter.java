package ir.nrdc.service.converter;

import ir.nrdc.model.dto.UserDto;
import ir.nrdc.model.entity.Role;
import ir.nrdc.model.entity.User;
import ir.nrdc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserDtoConverter {
    @Autowired
    RoleService roleService;

    public UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setNationalId(user.getNationalId());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().getRoleName().name());
        return userDto;
    }

    public User convertDtoToUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setNationalId(userDto.getNationalId());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(stringToRoleConverter(userDto.getRole()));
        try {
            user.setProfilePicture(userDto.getProfilePicture().getBytes());
        } catch (IOException e) {
            user.setProfilePicture(null);
        }
        return user;
    }

    public Role stringToRoleConverter(String roleName) {
        return roleService.findRoleByName(roleName);
    }
}
