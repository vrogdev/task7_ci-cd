package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.interfaces.OrderService;
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
 * Class {@code OrderController} represents endpoint of API which allows you to perform
 * operations on orders.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    private final DtoConverter<Order, OrderDto> orderConverter;

    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Autowired
    public OrderController(OrderService orderService, DtoConverter<Order, OrderDto> orderConverter, PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * This method is used to get page of all orders.
     * @param page page requested information
     * @return {@link PagedModel} of OrderDto model
     */
    @GetMapping
    public PagedModel<OrderDto> getAll(@PageableDefault(
            sort = {"id"},
            size = 5) Pageable page) {
        Page<Order> orders = orderService.getAllOrders(page);
        return pagedResourcesAssembler.toModel(orders, orderConverter);
    }

    /**
     * This method is used to get order dto by id.
     * @param orderId order's id
     * @return order dto
     */
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getById(@PathVariable("orderId") Long orderId){
        return orderConverter.toModel(orderService.getOrderById(orderId));
    }

    /**
     * This method is used to get all orders by user's id.
     * @param userId provided user's id
     * @param page page requested information
     * @return list of orders dto
     */
    @GetMapping("/users/{userId}")
    public PagedModel<OrderDto> ordersByUserId(
            @PathVariable long userId,
            @PageableDefault(
                    sort = {"id"},
                    size = 5) Pageable page) {
        Page<Order> orders = orderService.getAllOrdersByUserId(userId, page);

        return pagedResourcesAssembler.toModel(orders, orderConverter);
    }

    /**
     * This method is used to make order - buy certificate.
     * @param userId user's id
     * @param giftCertificateId gift certificate id
     * @return created {@link OrderDto} entity.
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("giftCertificateId") Long giftCertificateId) {
        Order order = orderService.createOrder(userId, giftCertificateId);
        return orderConverter.toModel(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertificateById(@PathVariable long id) {
        orderService.removeOrder(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Entity with id = " + id + " removed successfully");
    }
}
