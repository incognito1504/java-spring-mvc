package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Carts;
import vn.hoidanit.laptopshop.domain.Users;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
    Carts findByUser(Users user);
}
