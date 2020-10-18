package ir.nrdc.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDto {
    private String name;
    private AuthorDto author;
    private String publisher;
    private String ageGroup;
    private String subject;
    private String isbn;
    private int numberOfPages;
    private String publishDate;
    private int number;
}
