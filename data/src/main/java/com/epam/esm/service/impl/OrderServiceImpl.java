package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceExceptionMessages;
import com.epam.esm.service.interfaces.OrderService;
import com.epam.esm.service.util.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, GiftCertificateRepository giftCertificateRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public Order getOrderById(Long id) {
        IdentifiableValidator.validateId(id);

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException("Order with id " + id + " doesn't exist", ServiceExceptionCodes.NO_ENTITY_WITH_ID, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<Order> getAllOrders(Pageable page) {
        Page<Order> orders = orderRepository.findAll(page);
        checkPageBounds(page, orders);

        return orders;
    }

    private void checkPageBounds(Pageable page, Page<Order> orders) {
        int pageNumber = page.getPageNumber();
        int totalPages = orders.getTotalPages();

        if(pageNumber >= totalPages)
            throw new ServiceException(
                    "Page out of bounds",
                    ServiceExceptionCodes.BAD_ID,
                    HttpStatus.BAD_REQUEST);
    }

    @Override
    public void removeOrder(Long id) {
        IdentifiableValidator.validateId(id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("No order found for id = " + id));
        orderRepository.delete(order);
    }

    @Override
    public Order createOrder(Long userId, Long certificateId) {
        IdentifiableValidator.validateId(userId);
        IdentifiableValidator.validateId(certificateId);

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ServiceException(ServiceExceptionMessages.USER_ID_NOT_FOUND, ServiceExceptionCodes.NO_ENTITIES, HttpStatus.NOT_FOUND));

        GiftCertificate giftCertificate =
                giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                        new ServiceException(ServiceExceptionMessages.BAD_CERTIFICATE_ID, ServiceExceptionCodes.BAD_ID, HttpStatus.NOT_FOUND));

        Order order = new Order();

        order.setCost(giftCertificate.getPrice());
        order.setPurchaseTime(LocalDateTime.now());
        order.setCertificate(giftCertificate);
        order.setUser(user);

        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllOrdersByUserId(Long userId, Pageable page) {
        IdentifiableValidator.validateId(userId);

        Page<Order> orders = orderRepository.findAllByUser_Id(userId, page);

        if (orders.getTotalElements() == 0)
            throw new ServiceException(ServiceExceptionMessages.ORDER_NOT_FOUND_FOR_USER_ID,
                    ServiceExceptionCodes.NO_ENTITIES,
                    HttpStatus.NOT_FOUND);

        checkPageBounds(page, orders);
        return orders;
    }
}
