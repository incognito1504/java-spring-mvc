package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Products;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(
            ProductService productService,
            UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Products product = this.productService.fetchProductById(id).get();
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @RequestMapping(value = "/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Products> currentProduct = this.productService.fetchProductById(id);
        model.addAttribute("newProduct", currentProduct.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Products pr,
            BindingResult newProductBindingResult, @RequestParam("DarkLordFile") MultipartFile[] files) {
        // validate
        if (newProductBindingResult.hasErrors()) {
            return "/admin/product/update";
        }
        //
        Products currentProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currentProduct != null) {
            // update new image
            if (!files[0].isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(files, "product");
                currentProduct.setImage(img);
            }
            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setFactory(pr.getFactory());
            currentProduct.setTarget(pr.getTarget());

            this.productService.createProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Products());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String getDeleteProduct(Model model, @ModelAttribute("newProduct") Products pr) {
        this.productService.deleteProduct(pr.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Products> prs = this.productService.fetchProducts();
        model.addAttribute("products", prs);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Products());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(Model model,
            @ModelAttribute("newProduct") @Valid Products pr,
            BindingResult newProductBindingResult,
            @RequestParam("DarkLordFile") MultipartFile[] files) {
        // validate
        if (newProductBindingResult.hasErrors()) {
            return "/admin/product/create";
        }
        //
        String image = this.uploadService.handleSaveUploadFile(files, "product");
        pr.setImage(image);
        // save
        this.productService.createProduct(pr);
        return "redirect:/admin/product";
    }
}
