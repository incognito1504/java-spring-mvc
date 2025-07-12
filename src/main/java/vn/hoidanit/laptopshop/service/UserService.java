package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Roles;
import vn.hoidanit.laptopshop.domain.Users;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public Page<Users> getAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public List<Users> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public Users handleSave(Users user) {
        Users Phat = this.userRepository.save(user);
        System.out.println(Phat);
        return Phat;
    }

    public Users getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Roles getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public Users registerDTOtoUser(RegisterDTO registerDTO) {
        Users user = new Users();

        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword((registerDTO.getPassword()));
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public Users getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUser() {
        return this.userRepository.count();
    }

    public long countProduct() {
        return this.productRepository.count();
    }

    public long countOrder() {
        return this.orderRepository.count();
    }
}
