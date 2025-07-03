package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Carts;
import vn.hoidanit.laptopshop.domain.Products;
import vn.hoidanit.laptopshop.domain.Users;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(
            ProductRepository productRepository,
            CartRepository cartRepository,
            UserService userService,
            CartDetailRepository cartDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Products createProduct(Products pr) {
        return this.productRepository.save(pr);
    }

    public List<Products> fetchProducts() {
        return this.productRepository.findAll();
    }

    public Optional<Products> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        Users user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user da co cart hay chua
            Carts cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // create new cart
                Carts otherCart = new Carts();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }

            // save cart_detail
            // find product by id

            Optional<Products> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Products realProduct = productOptional.get();

                CartDetail oldCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldCartDetail == null) {
                    CartDetail newCartDetail = new CartDetail();
                    newCartDetail.setCart(cart);
                    newCartDetail.setProduct(realProduct);
                    newCartDetail.setPrice(realProduct.getPrice());
                    newCartDetail.setQuantity(1);

                    this.cartDetailRepository.save(newCartDetail);

                    // update cart(sum)
                    int count = cart.getSum() + 1;
                    cart.setSum(count);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", count);
                } else {
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldCartDetail);
                }

            }
        }
    }

    public Carts fetchByUser(Users user) {
        return this.cartRepository.findByUser(user);
    }
}
