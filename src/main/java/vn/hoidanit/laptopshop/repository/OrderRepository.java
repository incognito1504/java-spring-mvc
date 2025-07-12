package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Orders;
import vn.hoidanit.laptopshop.domain.Users;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(Users user);
}
