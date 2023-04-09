package com.epam.esm.dto.converter;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * This interface provides tool for converting {@link E} entities to {@link M} DTOs.
 * @param <E> entity type
 * @param <M> model DTO type.
 */
public interface DtoConverter<E, M extends RepresentationModel<?>> extends RepresentationModelAssembler<E, M> {

    /**
     *Converts the {@link E} entity to the {@link M} DTO.
     * @param entity {@link E} entity
     * @return converted {@link M} DTO.
     */
    M toModel(E entity);

    /**
     * Converts the {@link M} DTO to the {@link E} entity.
     * @param model {@link M} DTO
     * @return converted {@link E} entity
     */
    E toEntity(M model);
}
