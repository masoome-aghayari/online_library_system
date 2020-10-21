package ir.nrdc.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Getter
@Setter
public class UserDto {
    private int id;
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
    private MultipartFile profilePicture;
}
