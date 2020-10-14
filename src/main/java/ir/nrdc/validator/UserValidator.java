package ir.nrdc.validator;

import ir.nrdc.model.dto.UserDto;
import ir.nrdc.service.UserService;
import ir.nrdc.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    UserService userService;
    @Autowired
    UserDtoConverter userDtoConverter;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }
    @Override
    public void validate(Object o, Errors errors) {
        UserDto user = (UserDto) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        if (!user.getFirstName().matches("^[a-zA-Z]+"))
            errors.rejectValue("firstName", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        if (!user.getLastName().matches("^[a-zA-Z]+"))
            errors.rejectValue("lastName", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!user.getEmail().matches("^[\\w!#$%&'*+/=?`{|}~^-]+" +
                "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            errors.rejectValue("email", "Invalid.userDto.email");
        if (userService.isExistsUser(user.getNationalId()))
            errors.rejectValue("nationalId", "Duplicate.userDto.nationalId");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 16)
            errors.rejectValue("password", "Size.userDto.password");
        if (!user.getPassword().matches("\\A(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{8,16}\\z"))
            errors.rejectValue("password",
                    "Pattern.userDto.password");
        if (!user.getPassword().matches(user.getConfirmPassword()))
            errors.rejectValue("confirmPassword", "Diff.userDto.passwordConfirm");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "NotEmpty");
        if (userDtoConverter.stringToRoleConverter(user.getRole()) == null)
            errors.rejectValue("role", "Undefined.userDto.role");
    }
}
