package ir.nrdc.service;

import ir.nrdc.model.UserIds;
import ir.nrdc.model.dto.UserDto;
import ir.nrdc.model.entity.User;
import ir.nrdc.model.repository.UserRepository;
import ir.nrdc.model.repository.UserSpecifications;
import ir.nrdc.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDtoConverter userDtoConverter;
    @Autowired
    RoleService roleService;
    @Autowired
    Environment env;

    @Transactional
    public void registerUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole("MEMBER");
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

    @Transactional
    public int getTotalNumberOfPages(UserDto userDto) {
        long totalMatched = userRepository.count(UserSpecifications.findMaxMatch(userDto.getFirstName(),
                userDto.getLastName(), userDto.getNationalId()));
        int rowsNumberInPage = Integer.parseInt(env.getProperty("Page.Rows"));
        double pages = (double) totalMatched / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }

    @Transactional
    public List<UserDto> findMaxMatch(UserDto userDto, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "firstName", "lastName");
        Page<User> matchedUsers = userRepository.findAll(UserSpecifications.findMaxMatch(userDto.getFirstName(),
                userDto.getLastName(), userDto.getNationalId()), pageable);
        return userDtoConverter.convertUserPageToDtoPage(matchedUsers).getContent();
    }

    @Transactional
    public void deleteMembers(List<UserIds> memberIds) {
        memberIds.forEach(userId -> userRepository.deleteById(userId.getUserId()));
    }

    @Transactional
    public void editMember(UserDto member) {
       userRepository.updateMember(member.getId(), member.getFirstName(), member.getLastName(), member.getNationalId(),
                member.getMobileNumber(), member.getEmail());
    }
}