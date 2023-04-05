package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverterImpl implements DtoConverter<User, UserDto> {
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public UserDto toModel(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public User toEntity(UserDto model) {
        return modelMapper.map(model, User.class);
    }
}
