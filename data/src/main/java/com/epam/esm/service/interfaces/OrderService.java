package com.epam.esm.service.interfaces;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order getOrderById(Long id);

    Page<Order> getAllOrders(Pageable page);

    void removeOrder(Order item);

    Page<Order> getAllOrdersByUserId(Long userId, Pageable page);

    Order createOrder(Long userId, Long certificateId);
}
