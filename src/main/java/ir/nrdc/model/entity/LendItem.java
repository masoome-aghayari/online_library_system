package ir.nrdc.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class LendItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Book book;
    @ManyToOne
    private User member;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;
}
