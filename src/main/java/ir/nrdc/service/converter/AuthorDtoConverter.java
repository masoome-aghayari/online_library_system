package ir.nrdc.service.converter;

import ir.nrdc.model.dto.AuthorDto;
import ir.nrdc.model.entity.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorDtoConverter {

    public Author convertDtoToAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setFamily(authorDto.getFamily());
        return author;
    }

    public AuthorDto convertAuthorToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(author.getName());
        authorDto.setFamily(author.getFamily());
        return authorDto;
    }
}
