package ir.nrdc.model.dto;

import ir.nrdc.model.entity.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LendItemDto {
    private BookDto book;
    private UserDto member;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;
}
