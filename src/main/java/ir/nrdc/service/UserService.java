package ir.nrdc.service;

import ir.nrdc.model.dto.UserDto;
import ir.nrdc.model.entity.User;
import ir.nrdc.model.repository.UserRepository;
import ir.nrdc.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDtoConverter userDtoConverter;

    @Transactional
    public void registerUser(UserDto userDto) {
        userRepository.save(userDtoConverter.convertDtoToUser(userDto));
    }

    @Transactional
    public boolean isExistsUser(String nationalId) {
        return userRepository.countByNationalId(nationalId) != 0;
    }

    @Transactional
    public UserDto findByNationalId(String nationalId) {
        Optional<User> found = userRepository.findByNationalId(nationalId);
        return found.map(user -> userDtoConverter.convertUserToDto(user)).orElse(null);
    }
}
