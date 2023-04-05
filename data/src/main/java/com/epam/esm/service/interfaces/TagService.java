package com.epam.esm.service.interfaces;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Page<Tag> getAllTags(Pageable page);

    Tag getTagById(long id);

    void insertTag(Tag item);

    void removeTagById(long id);

    Tag getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
}
