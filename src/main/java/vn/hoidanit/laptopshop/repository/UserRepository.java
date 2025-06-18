package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Users;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users save(Users DarkLord);

    List<Users> findOneByEmail(String email);

    List<Users> findAll();

    Users findById(long id);

    void deleteById(long id);
}
