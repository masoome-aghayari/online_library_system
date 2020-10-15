package ir.nrdc.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int age;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String gender;
    private String nationalId;
    @OneToOne
    private Role role;
    @OneToMany
    private List<BorrowItem> borrowedBooks;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;
}
