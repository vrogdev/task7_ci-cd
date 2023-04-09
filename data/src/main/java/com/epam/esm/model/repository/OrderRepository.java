package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@code OrderRepository} describes operations with Order entity extending JPA repository to work with database tables.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Method for retrieving list of orders by given user's id.
     * @param id user's id
     * @param page {@code Pageable} page object contains parameters for page request
     * @return list of orders
     */
    Page<Order> findAllByUser_Id(long id, Pageable page);
}
