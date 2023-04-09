package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl service;


    @Test
    void getOrderById() {

    }

    @Test
    void getAllOrders() {
        PageRequest page = PageRequest.of(1, 5);
        PageImpl<Order> orders = new PageImpl<>(List.of(new Order()));
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orders);
        Page<Order> actualOrders = service.getAllOrders(page);
        assertEquals(actualOrders, orders);
    }

    @Test
    void removeOrder() {
        doNothing().when(orderRepository).delete(any(Order.class));
        service.removeOrder(new Order());
        verify(orderRepository, times(1)).delete(new Order());
    }

    @Test
    void createOrder() {
        long userId = 1L;
        long certificateId = 1L;

        User user = new User(userId, "username");
        GiftCertificate certificate = new GiftCertificate(certificateId, "Certificate_1", "Description", 100.50, 10, LocalDateTime.now(), LocalDateTime.now(), null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(giftCertificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));

        Order order = new Order();

        order.setCost(certificate.getPrice());
        order.setCertificate(certificate);
        order.setUser(user);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order orderActual = service.createOrder(userId, certificateId);

        assertEquals(orderActual, order);
    }

    @Test
    void getAllOrdersByUserId() {
        long userId = 1L;
        PageRequest page = PageRequest.of(1, 5);

        Page<Order> orders = new PageImpl<>(List.of(new Order()));
        when(orderRepository.findAllByUser_Id(userId, page)).thenReturn(orders);

        Page<Order> ordersActual = service.getAllOrdersByUserId(userId, page);

        assertEquals(ordersActual, orders);
    }

    @Test
    void getAllOrdersByUserIdInvalid() {
        assertThrows(ServiceException.class, () -> service.getAllOrdersByUserId(-1L, null));

        long userId = 1L;
        PageRequest page = PageRequest.of(1, 5);

        Page<Order> orders = new PageImpl<>(new ArrayList<Order>());
        when(orderRepository.findAllByUser_Id(userId, page)).thenReturn(orders);

        assertThrows(ServiceException.class, () -> service.getAllOrdersByUserId(userId, page));
    }
}