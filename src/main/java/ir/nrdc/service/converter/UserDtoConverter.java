package ir.nrdc.service.converter;

import ir.nrdc.model.dto.UserDto;
import ir.nrdc.model.entity.Role;
import ir.nrdc.model.entity.User;
import ir.nrdc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        userDto.setMobileNumber(user.getMobileNumber());
        userDto.setRole(user.getRole().getRoleName());
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

    public ArrayList<UserDto> convertUserListToDtoList(List<User> pendingUsers) {
        return pendingUsers.stream().map(this::convertUserToDto).collect(Collectors.toCollection(ArrayList::new));
    }

    public Page<UserDto> convertUserPageToDtoPage(Page<User> userPage) {
        return new PageImpl<>(convertUserListToDtoList(userPage.getContent()));
    }
}