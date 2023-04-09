package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class {@code TagController} represents endpoint of API which allows you to perform
 * operations on tags.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    private final DtoConverter<Tag, TagDto> tagConverter;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;

    @Autowired
    public TagController(TagService tagService, DtoConverter<Tag, TagDto> tagConverter, PagedResourcesAssembler<Tag> pagedResourcesAssembler) {
        this.tagService = tagService;
        this.tagConverter = tagConverter;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Returns all {@link Tag} entities from database.
     *
     * @param page page requested information
     * @return a {@link PagedModel} of {@link TagDto} entities with HATEOAS.
     */
    @GetMapping
    public PagedModel<TagDto> allTags(@PageableDefault(
            sort = {"id"},
            size = 5) Pageable page) {
        Page<Tag> tags = tagService.getAllTags(page);
        return pagedResourcesAssembler.toModel(tags, tagConverter);
    }

    /**
     * Returns a {@link Tag} by its ID from data source.
     *
     * @param id a {@link Tag} ID.
     * @return a {@link Tag} entity.
     */
    @GetMapping("/{id}")
    public Tag tagById(@PathVariable long id) {
        return tagService.getTagById(id);
    }

    /**
     * returns the most used tag of the User with the highest cost of orders
     *
     * @return Tag with given parameters
     */
    @GetMapping("/popular")
    public Tag tagMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() {
        return tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
    }

    /**
     * This method is used to create tag.
     *
     * @param tag to create
     * @return string with result
     */
    @PostMapping
    public ResponseEntity<String> createTag(@RequestBody Tag tag) {
        tagService.insertTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tag has been successfully added");
    }

    /**
     * This method is used to delete tag from certificate.
     *
     * @param id of tag to delete
     * @return string with status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        tagService.removeTagById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tag with id = " + id + " successfully deleted");
    }
}
