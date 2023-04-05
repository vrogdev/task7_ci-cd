package com.epam.esm.dto.converter;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface DtoConverter<E, M extends RepresentationModel<?>> extends RepresentationModelAssembler<E, M> {
    M toModel(E entity);
    E toEntity(M model);
}
