package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Roles;
import vn.hoidanit.laptopshop.domain.Users;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
}
