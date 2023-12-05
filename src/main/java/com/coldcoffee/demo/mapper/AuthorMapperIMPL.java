package com.coldcoffee.demo.mapper;
import com.coldcoffee.demo.DTO.AuthorDTO;
import com.coldcoffee.demo.model.AuthorEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperIMPL implements Mapper<AuthorEntity, AuthorDTO> {

    private final ModelMapper modelMapper;

    public AuthorMapperIMPL(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDTO mapFromEntityToDTO(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDTO.class);
    }

    @Override
    public AuthorEntity mapFromDTOToEntity(AuthorDTO authorDTO) {
        return modelMapper.map(authorDTO, AuthorEntity.class);
    }
}
