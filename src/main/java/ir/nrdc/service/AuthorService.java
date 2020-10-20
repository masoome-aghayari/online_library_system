package ir.nrdc.service;

import ir.nrdc.model.dto.AuthorDto;
import ir.nrdc.model.entity.Author;
import ir.nrdc.model.repository.AuthorRepository;
import ir.nrdc.service.converter.AuthorDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorDtoConverter authorDtoConverter;

    @Transactional
    public Author addNewAuthor(AuthorDto authorDto) {
        Author author = authorDtoConverter.convertDtoToAuthor(authorDto);
        return authorRepository.save(author);
    }

    @Transactional
    public boolean isExists(AuthorDto authorDto) {
        return authorRepository.findByNameAndFamily(authorDto.getName(), authorDto.getFamily()).isPresent();
    }

    public Author findByNameAndFamily(AuthorDto authorDto) {
        return authorRepository.findByNameAndFamily(authorDto.getName(), authorDto.getFamily())
                .orElse(null);
    }
}
