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

import javax.smartcardio.CardException;
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

    public String addtoCart(CartDTO cartDTO){
        try{
            User user=userRepository.findById(cartDTO.getUserId()).orElse(null);
            Product product=productRepository.findById(cartDTO.getProductId()).orElse(null);
            if(user == null || product==null){
                return "User Or Product Are Not Found!!";
            }
            Cart cart=new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        CartItem cartItem=new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartDTO.getQuantity());
        cartItemRepository.save(cartItem);
        } catch (Exception e) {
            throw new RuntimeException("Add Cart Service Error!!!");
        }
        return "Product Added To Cart";
    }
    public List<CartItem> getcart(Long id){
        Cart cart=cartRepository.findByUserId(id);
        if(cart == null){
            return null;
        }
        return cart.getItems();

    }
}
