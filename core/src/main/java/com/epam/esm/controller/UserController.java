package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class {@code UserController} represents endpoint of API which allows you to perform
 * operations on users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final DtoConverter<User, UserDto> userConverter;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    @Autowired
    public UserController(UserService userService, DtoConverter<User, UserDto> userConverter, PagedResourcesAssembler<User> pagedResourcesAssembler) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * gets all {@link User} entities from database.
     *
     * @param page page requested information
     * @return a {@link PagedModel} of {@link UserDto} entities with hateoas.
     */
    @GetMapping
    public PagedModel<UserDto> allUsers(@PageableDefault(
            sort = {"id"},
            size = 5) Pageable page) {
        Page<User> users = userService.getAllUsers(page);
        return pagedResourcesAssembler.toModel(users, userConverter);
    }

    /**
     * get {@link User} entity with specified id from database.
     *
     * @param id ID of user.
     * @return @link UserDto} entity with hateoas.
     */
    @GetMapping("/{id}")
    public UserDto userById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return userConverter.toModel(user);
    }
}
