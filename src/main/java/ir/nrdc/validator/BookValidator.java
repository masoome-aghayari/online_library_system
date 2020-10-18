package ir.nrdc.validator;

import ir.nrdc.model.dto.BookDto;
import ir.nrdc.utils.AgeGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
@PropertySource("classpath:constant_numbers.properties")
public class BookValidator implements Validator {
    @Autowired
    Environment env;

    @Override
    public boolean supports(Class<?> aClass) {
        return BookDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BookDto bookDto = (BookDto) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "NotEmpty");
        if (!bookDto.getSubject().matches("^[a-zA-Z]+([. ]*[a-zA-Z]+)*"))
            errors.rejectValue("subject", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author.name", "NotEmpty");
        if (!bookDto.getAuthor().getName().matches("^[a-zA-Z]+(([a-zA-Z. ])?[a-zA-Z]*)*"))
            errors.rejectValue("author.name", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author.family", "NotEmpty");
        if (!bookDto.getAuthor().getFamily().matches("^[a-zA-Z]+(([a-zA-Z. ])?[a-zA-Z]*)*"))
            errors.rejectValue("author.family", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "publisher", "NotEmpty");
        if (!bookDto.getPublisher().matches("^[a-zA-Z]+(([a-zA-Z. ])?[a-zA-Z]*)*"))
            errors.rejectValue("publisher", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", "NotEmpty");
        if (!bookDto.getIsbn().matches("^[0-9]{10}|[0-9]{13}"))
            errors.rejectValue("isbn", "Length.ISBN");
        else if (!isValidISBN(bookDto.getIsbn()))
            errors.rejectValue("isbn", "Invalid.ISBN");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfPages", "NotEmpty");
        int maxPages = Integer.parseInt(env.getProperty("Max.NumberOfPages.Book"));
        int minPages = Integer.parseInt(env.getProperty("Min.NumberOfPages.Book"));
        if ((bookDto.getNumberOfPages() < minPages) || (bookDto.getNumberOfPages() > maxPages))
            errors.rejectValue("numberOfPages", "Invalid.NumberOfPages");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "publishDate", "NotEmpty");
        LocalDate publishDate = stringToLocalDate(bookDto.getPublishDate());
        if (publishDate == null || publishDate.compareTo(LocalDate.now()) > 0)
            errors.rejectValue("publishDate", "Invalid.PublishDate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ageGroup", "NotEmpty");
        if (!AgeGroup.getStringList().contains(bookDto.getAgeGroup()))
            errors.rejectValue("ageGroup", "Invalid.AgeGroup");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "NotEmpty");
        if (bookDto.getNumber() < 1)
            errors.rejectValue("number", "Invalid.Number");
    }

    private boolean isValidISBN(String isbn) {
        int sum = 0;
        int tenDigitISBN = Integer.parseInt(env.getProperty("ISBN.Length.10"));
        int tenDigitISBNDivider = Integer.parseInt(env.getProperty("ISBN.Length.10.Divider"));
        int thirteenDigitISBNDivider = Integer.parseInt(env.getProperty("ISBN.Length.13.Divider"));
        if (isbn.length() == tenDigitISBN) {
            sum = IntStream.range(0, 9).map(i -> Character.getNumericValue(isbn.charAt(i)) *
                    (tenDigitISBN - i)).sum();
            return sum % tenDigitISBNDivider == 0;
        }
        for (int i = 0; i < isbn.length(); i++) {
            if (i % 2 == 0)
                sum += Character.getNumericValue(isbn.charAt(i));
            else
                sum += Character.getNumericValue(isbn.charAt(i)) * 3;
        }
        return sum % thirteenDigitISBNDivider == 0;
    }

    public LocalDate stringToLocalDate(String dateString) {
        if (Objects.isNull(dateString) || dateString.length() == 0)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}