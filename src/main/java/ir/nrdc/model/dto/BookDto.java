package ir.nrdc.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BookDto implements Serializable {
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
