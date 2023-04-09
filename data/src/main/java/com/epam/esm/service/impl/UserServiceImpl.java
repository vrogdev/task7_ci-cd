package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceExceptionMessages;
import com.epam.esm.service.interfaces.UserService;
import com.epam.esm.service.util.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public User getUserById(Long id) {
        IdentifiableValidator.validateId(id);
        return getUserIfExists(id);
    }

    @Override
    public void insertUser(User model) {
        if (model == null) {
            throw new ServiceException("User is null", ServiceExceptionCodes.BAD_TAG_NAME, HttpStatus.BAD_REQUEST);
        }
        userRepository.save(model);
    }

    @Override
    public void removeUser(User item) {
        User user = getUserIfExists(item.getId());

        userRepository.delete(user);
    }

    @Override
    public void updateUser(User entity) {
        User user = getUserIfExists(entity.getId());
        user.setOrders(entity.getOrders());
    }

    private User getUserIfExists(long id) {
        IdentifiableValidator.validateId(id);
        return userRepository.findById(id).orElseThrow(() -> new ServiceException(ServiceExceptionMessages.USER_ID_NOT_FOUND,
                ServiceExceptionCodes.NO_ENTITIES,
                HttpStatus.NOT_FOUND));
    }

}
