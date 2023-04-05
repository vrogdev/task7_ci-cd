package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceExceptionMessages;
import com.epam.esm.service.interfaces.TagService;
import com.epam.esm.service.util.IdentifiableValidator;
import com.epam.esm.service.util.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<Tag> getAllTags(Pageable page) {
        return tagRepository.findAll(page);
    }

    @Override
    public Tag getTagById(long id) throws ServiceException {
        IdentifiableValidator.validateId(id);
        Optional<Tag> byId = tagRepository.findById(id);
        return byId.orElseThrow(RuntimeException::new);
    }

    @Override
    public void insertTag(Tag item) throws ServiceException {
        TagValidator.validate(item);
        tagRepository.save(item);
    }

    @Override
    public void removeTagById(long id) throws ServiceException {
        IdentifiableValidator.validateId(id);
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() {
        return tagRepository.findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders()
                .orElseThrow(() -> new ServiceException(ServiceExceptionMessages.TAG_NOT_FOUND,
                        ServiceExceptionCodes.NO_ENTITIES,
                        HttpStatus.NOT_FOUND));
    }

}
