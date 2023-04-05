package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoConverterImpl implements DtoConverter<Order, OrderDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public OrderDto toModel(Order entity) {
        return modelMapper.map(entity, OrderDto.class);
    }

    @Override
    public Order toEntity(OrderDto model) {
        return modelMapper.map(model, Order.class);
    }
}
