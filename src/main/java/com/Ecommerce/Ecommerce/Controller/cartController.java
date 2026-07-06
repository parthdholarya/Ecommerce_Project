package com.Ecommerce.Ecommerce.Controller;

import com.Ecommerce.Ecommerce.DTO.CartDTO;
import com.Ecommerce.Ecommerce.Model.Cart;
import com.Ecommerce.Ecommerce.Model.CartItem;
import com.Ecommerce.Ecommerce.Service.cartService;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class cartController {
    private final cartService cartService;

    public cartController(cartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/addtocart")
    public ResponseEntity<String> addtoCart(@RequestBody CartDTO cartDTO){
        String response=cartService.addtoCart(cartDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{userid}")
    public ResponseEntity<?> getcart(@PathVariable Long userid){
        List<CartItem> item=cartService.getcart(userid);
        if(item == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        return ResponseEntity.ok(item);
    }
}
