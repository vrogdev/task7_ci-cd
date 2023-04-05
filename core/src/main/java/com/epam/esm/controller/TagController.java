package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
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

    @GetMapping
    public PagedModel<TagDto> allTags(@PageableDefault(
            sort = {"id"},
            size = 5) Pageable page) {
        Page<Tag> tags = tagService.getAllTags(page);
        return pagedResourcesAssembler.toModel(tags, tagConverter);
    }

    @GetMapping("/{id}")
    public Tag tagById(@PathVariable long id){
        return tagService.getTagById(id);
    }

    @GetMapping("/popular")
    public Tag tagMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders()  {
        return tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
    }

    @PostMapping
    public ResponseEntity<String> createTag(@RequestBody Tag tag) throws ServiceException {
        tagService.insertTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tag has been successfully added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) throws ServiceException {
        tagService.removeTagById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tag with id = " + id + " successfully deleted");
    }
}
