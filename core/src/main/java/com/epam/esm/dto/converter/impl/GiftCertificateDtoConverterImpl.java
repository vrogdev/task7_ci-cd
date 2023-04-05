package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.GiftCertificate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateDtoConverterImpl implements DtoConverter<GiftCertificate, GiftCertificateDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public GiftCertificateDto toModel(GiftCertificate entity) {
        return modelMapper.map(entity, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto model) {
        return modelMapper.map(model, GiftCertificate.class);
    }
}
