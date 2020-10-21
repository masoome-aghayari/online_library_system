package ir.nrdc.service.converter;

import ir.nrdc.model.dto.LendItemDto;
import ir.nrdc.model.entity.LendItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LendItemDtoConverter {
    @Autowired
    BookDtoConverter bookDtoConverter;
    @Autowired
    UserDtoConverter userDtoConverter;

    public LendItem convertDtoToLendItem(LendItemDto lendItemDto){
        LendItem lendItem = new LendItem();
        lendItem.setBook(bookDtoConverter.convertDtoToBook(lendItemDto.getBook()));
    }
}
