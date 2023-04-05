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

    @GetMapping
    public PagedModel<UserDto> allUsers(@PageableDefault(
            sort = {"id"},
            size = 5) Pageable page) {
        Page<User> users = userService.getAllUsers(page);
        return pagedResourcesAssembler.toModel(users, userConverter);
    }

    @GetMapping("/{id}")
    public UserDto userById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return userConverter.toModel(user);
    }
}
