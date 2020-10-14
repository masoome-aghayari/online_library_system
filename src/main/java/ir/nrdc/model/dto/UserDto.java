package ir.nrdc.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int age;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String confirmPassword;
    private String gender;
    private String nationalId;
    private String Role;
}
