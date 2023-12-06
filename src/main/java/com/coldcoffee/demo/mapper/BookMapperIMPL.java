package com.coldcoffee.demo.mapper;
import com.coldcoffee.demo.DTO.BookDTO;
import com.coldcoffee.demo.model.BookEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperIMPL implements Mapper<BookEntity, BookDTO> {

    private final ModelMapper modelMapper;

    public BookMapperIMPL(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDTO mapFromEntityToDTO(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDTO.class);
    }

    @Override
    public BookEntity mapFromDTOToEntity(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, BookEntity.class);
    }
}
