package ir.nrdc.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String subject;
    @ManyToOne
    private Author author;
    private String publisher;
    private String ageGroup;
    private String isbn;
    private int numberOfPages;
    private LocalDate publishDate;
    private int number;
}
