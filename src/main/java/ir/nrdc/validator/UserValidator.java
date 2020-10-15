package ir.nrdc.validator;

import ir.nrdc.model.dto.UserDto;
import ir.nrdc.service.UserService;
import ir.nrdc.service.converter.UserDtoConverter;
import ir.nrdc.utils.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.IntStream;

@Component
public class UserValidator implements Validator {
    @Autowired
    UserService userService;
    @Autowired
    UserDtoConverter userDtoConverter;
    @Autowired
    Environment env;

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
        if (userService.isExistsUser(user.getEmail()))
            errors.rejectValue("email", "Duplicate.userDto.email");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nationalId", "NotEmpty");
        String nationalId = user.getNationalId();
        if (!isValidNationalId(nationalId))
            errors.rejectValue("nationalId", "Invalid.NationalId");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 16)
            errors.rejectValue("password", "Size.userDto.password");
        if (!user.getPassword().matches("\\A(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{8,16}\\z"))
            errors.rejectValue("password", "Pattern.userDto.password");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty");
        if (!user.getPassword().matches(user.getConfirmPassword()))
            errors.rejectValue("confirmPassword", "Diff.userDto.passwordConfirm");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "NotEmpty");
        int minimumAge = Integer.parseInt(env.getProperty("Limit.Age"));
        if (user.getAge() < 1)
            errors.rejectValue("age", "Invalid.Age");
        else if (user.getAge() < minimumAge)
            errors.rejectValue("age", "Young.Age");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNumber", "NotEmpty");
        if (user.getMobileNumber().length() != 10 || !user.getMobileNumber().matches("9((0[1-3]|5)|(1[0-9])|" +
                "(2[0-2])|(3(1|[3-9]))|(9[0-1]))[0-9]{7}"))
            errors.rejectValue("mobileNumber", "Invalid.MobileNumber");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty");
        Gender gender = Gender.valueOf(user.getGender().toUpperCase());
        if (gender.compareTo(Gender.MALE) != 0 && gender.compareTo(Gender.FEMALE) != 0)
            errors.rejectValue("gender", "Invalid.Gender");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profilePicture", "NotEmpty");
        MultipartFile profilePic = user.getProfilePicture();
        if (!isImage(profilePic))
            errors.rejectValue("profilePicture", "Error.Not.Image.File");
        else if (!isValidSize(profilePic))
            errors.rejectValue("profilePicture", "Error.Max.Size.Image");
    }

    private boolean isValidNationalId(String nationalId) {
        if (nationalId.length() != 10)
            return false;
        int remainder, lastDigit;
        int sum = IntStream.range(0, 9).map(i -> Character.getNumericValue(nationalId.charAt(i)) * (10 - i)).sum();
        remainder = sum % 11;
        lastDigit = remainder < 2 ? remainder : 11 - remainder;
        return nationalId.charAt(9) == lastDigit;
    }

    private boolean isImage(MultipartFile file) {
        String fileContentType = file.getContentType();
        return fileContentType != null && fileContentType.contains("image");
    }

    private boolean isValidSize(MultipartFile file) {
        int maxSize = Integer.parseInt(env.getProperty("Max.Size.Image"));
        return file.getSize() <= maxSize;
    }
}
