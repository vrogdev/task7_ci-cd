package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceExceptionMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private final Tag TAG_1 = new Tag(1, "tag1");
    private final Tag TAG_2 = new Tag(2, "tag2");
    private final Tag TAG_3 = new Tag(3, "tag3");
    private final Tag TAG_4 = new Tag(4, "tag4");
    private final Tag TAG_5 = new Tag(5, "Tag5");

    @Mock
    private TagRepository repo;

    @InjectMocks
    private TagServiceImpl tagService;


    @Test
    void getAllTagsValid() {
        List<Tag> tags = List.of(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);

        Page<Tag> pageOfTags = new PageImpl<>(tags);
        PageRequest page = PageRequest.of(0, 5);


        Mockito.when(repo.findAll(page)).thenReturn(pageOfTags);
        Page<Tag> tags_actual = tagService.getAllTags(page);

        assertEquals(tags_actual, pageOfTags);
    }

    @Test
    void getAllTagsInvalid() {
        assertThrows(ServiceException.class, () -> tagService.getTagById(0));
        assertThrows(ServiceException.class, () -> tagService.getTagById(-1));
    }

    @Test
    void getTagByIdValid() {
        Tag tag = new Tag(1, "tag1");

        Mockito.when(repo.findById(tag.getId())).thenReturn(Optional.of(tag));
        Tag actual = tagService.getTagById(tag.getId());
        assertEquals(tag, actual);
    }

    @Test
    void getTagByIdNotValid() {
        ServiceException exception = assertThrows(ServiceException.class, () -> tagService.getTagById(-1));
        assertEquals(ServiceExceptionMessages.BAD_ID, exception.getMessage());
        assertEquals(ServiceExceptionCodes.BAD_ID, exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void insertTag() {
        Tag newTag = new Tag(0, "tagName5");
        Mockito.when(repo.save(newTag)).thenReturn(TAG_1);
        tagService.insertTag(newTag);
        Mockito.verify(repo, Mockito.times(1)).save(newTag);
    }

    @Test
    void insertTagInvalid() {
        Tag newTag = new Tag(0, "ta");
        assertThrows(ServiceException.class, () -> tagService.insertTag(newTag));
    }

    @Test
    void removeTagById() {
        long id = 1L;
        Mockito.doNothing().when(repo).deleteById(id);
        tagService.removeTagById(id);
        Mockito.verify(repo, Mockito.atLeastOnce()).deleteById(id);
    }

    @Test
    void removeTagByIdInvalid() {
        assertThrows(ServiceException.class, () -> tagService.removeTagById(-1));
    }
}