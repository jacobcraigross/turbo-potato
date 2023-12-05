package com.coldcoffee.demo.mapper;


public interface Mapper<ModelEntity, ModelDTO> {
    ModelDTO mapFromEntityToDTO(ModelEntity modelEntity);
    ModelEntity mapFromDTOToEntity(ModelDTO modelDTO);
}
