package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}