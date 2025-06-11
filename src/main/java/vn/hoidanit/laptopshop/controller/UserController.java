package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("dangthuanphat465@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("eric", "test");
        model.addAttribute("hoidanit", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/view/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        System.out.println("check pack id = " + id);
        model.addAttribute("id", id);
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/view";
    }

    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User DarkLord) {
        System.out.println("run here" + DarkLord);
        this.userService.handleSave(DarkLord);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String getUpdateUser(Model model, @ModelAttribute("newUser") User DarkLord) {
        User currentUser = this.userService.getUserById(DarkLord.getId());
        if (currentUser != null) {
            currentUser.setAddress(DarkLord.getAddress());
            currentUser.setFullName(DarkLord.getFullName());
            currentUser.setPhone(DarkLord.getPhone());

            this.userService.handleSave(currentUser); // day la upsert
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String getDeleteUser(Model model, @ModelAttribute("newUser") User DarkLord) {
        this.userService.deleteAUser(DarkLord.getId());
        return "redirect:/admin/user";
    }

}
