package ir.nrdc.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BookDto implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("author")
    private AuthorDto author;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("ageGroup")
    private String ageGroup;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("numberOfPages")
    private int numberOfPages;
    @JsonProperty("publishDate")
    private String publishDate;
    @JsonProperty("number")
    private int number;
}
