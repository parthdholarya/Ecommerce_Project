package com.Ecommerce.Ecommerce.Service;

import com.Ecommerce.Ecommerce.DTO.CartDTO;
import com.Ecommerce.Ecommerce.Model.Cart;
import com.Ecommerce.Ecommerce.Model.CartItem;
import com.Ecommerce.Ecommerce.Model.Product;
import com.Ecommerce.Ecommerce.Model.User;
import com.Ecommerce.Ecommerce.Repository.CartItemRepository;
import com.Ecommerce.Ecommerce.Repository.CartRepository;
import com.Ecommerce.Ecommerce.Repository.ProductRepository;
import com.Ecommerce.Ecommerce.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class cartService {

    private final CartItemRepository cartItemRepository;

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public cartService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String addtoCart(CartDTO cartDTO) {

        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartDTO.getQuantity());

        cartItemRepository.save(cartItem);

        return "Product Added To Cart";
    }
    public List<CartItem> getcart(Long id){
        Cart cart=cartRepository.findByUserId(id);
        if(cart == null){
            return null;
        }
        return cart.getItems();

    }

    public CartItem updateQuantity(Long cartItemId, int quantity) {

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        if (quantity > cartItem.getProduct().getStock()) {
            throw new RuntimeException("Insufficient stock");
        }

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    public String removeItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);

        return "Product removed from cart successfully";
    }

    public double calculateCartTotal(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        double total = 0;

        for (CartItem cartItem : cartItems) {

            double price = cartItem.getProduct().getPrice();
            int quantity = cartItem.getQuantity();

            total = total + (price * quantity);
        }

        return total;
    }
}
