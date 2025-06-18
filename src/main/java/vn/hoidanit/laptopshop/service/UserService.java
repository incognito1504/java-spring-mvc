package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Users;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public List<Users> getAllUsers() {
        return this.userRepository.findAll();
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
}
