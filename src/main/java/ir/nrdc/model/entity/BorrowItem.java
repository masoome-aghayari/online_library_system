package ir.nrdc.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BorrowItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Book book;
    @Temporal(TemporalType.DATE)
    private LocalDate borrowDate;
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;
}
