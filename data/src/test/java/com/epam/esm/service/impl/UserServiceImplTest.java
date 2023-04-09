package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void getAllUsers() {
        PageImpl<User> page = new PageImpl<>(new ArrayList<>());
        when(repo.findAll(any(Pageable.class))).thenReturn(page);

        assertEquals(page, service.getAllUsers(PageRequest.of(1, 5)));
    }

    @Test
    void getUserById() {
        long userId = 1L;
        User user = new User(1L, "username");
        when(repo.findById(any(Long.class))).thenReturn(Optional.of(user));

        assertEquals(service.getUserById(userId), user);
    }

    @Test
    void getUserByIdInvalid() {
        assertThrows(ServiceException.class, () -> service.getUserById(-1L));
        when(repo.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.getUserById(5L));

    }

}