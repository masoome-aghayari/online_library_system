package ir.nrdc.service;

import ir.nrdc.model.dto.LendItemDto;
import ir.nrdc.model.repository.LendItemRepository;
import ir.nrdc.service.converter.LendItemDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:constant_numbers.properties")
public class LendItemService {
    @Autowired
    LendItemRepository lendItemRepository;
    @Autowired
    LendItemDtoConverter lendItemDtoConverter;
    @Autowired
    Environment env;

    @Transactional
    public void saveLendItem(LendItemDto lendItemDto) {

    }

    @Transactional
    public boolean lendBook(LendItemDto lendItemDto) {
        if (lendItemRepository.countByBook_IsbnAndReturned(lendItemDto.getBook().getIsbn(), false) == 0)
            lendBookDtoConverter.convertDtoToLendItem(lendItemDto);
    }
}
