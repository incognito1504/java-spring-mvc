package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Users;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            UserService userService,
            UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<Users> arrUsers = this.userService.getAllUsersByEmail("dangthuanphat465@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("eric", "test");
        model.addAttribute("hoidanit", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<Users> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        // System.out.println("check pack id = " + id);
        model.addAttribute("id", id);
        Users user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new Users());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid Users DarkLord,
            BindingResult newUserBindingResult,
            @RequestParam("DarkLordFile") MultipartFile[] files) {
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        // validate
        if (newUserBindingResult.hasErrors()) {
            return "/admin/user/create";
        }
        //
        String avatar = this.uploadService.handleSaveUploadFile(files, "avatar");
        String hashPassword = this.passwordEncoder.encode(DarkLord.getPassword());

        DarkLord.setAvatar(avatar);
        DarkLord.setPassword(hashPassword);
        DarkLord.setRole(this.userService.getRoleByName(DarkLord.getRole().getName()));
        // save
        this.userService.handleSave(DarkLord);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        Users currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String getUpdateUser(Model model, @ModelAttribute("newUser") Users DarkLord) {
        Users currentUser = this.userService.getUserById(DarkLord.getId());
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
        model.addAttribute("newUser", new Users());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String getDeleteUser(Model model, @ModelAttribute("newUser") Users DarkLord) {
        this.userService.deleteAUser(DarkLord.getId());
        return "redirect:/admin/user";
    }

}
