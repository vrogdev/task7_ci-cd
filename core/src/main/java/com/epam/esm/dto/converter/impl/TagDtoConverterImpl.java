package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagDtoConverterImpl implements DtoConverter<Tag, TagDto> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Tag toEntity(TagDto model) {
        return modelMapper.map(model, Tag.class);
    }

    @Override
    public TagDto toModel(Tag entity) {
        return modelMapper.map(entity, TagDto.class);
    }
}
