package com.epam.esm.service.interfaces;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getUserById(Long id);

    Page<User> getAllUsers(Pageable page);

    void insertUser(User item);

    void removeUser(User item);

    void updateUser(User item);
    Tag getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders(Long userId);
}
