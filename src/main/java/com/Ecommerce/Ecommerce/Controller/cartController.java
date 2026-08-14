package com.Ecommerce.Ecommerce.Controller;

import com.Ecommerce.Ecommerce.DTO.CartDTO;
import com.Ecommerce.Ecommerce.Model.CartItem;
import com.Ecommerce.Ecommerce.Service.cartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
        if(Objects.equals(response, null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Not Added To Cart.");
        }
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

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {

        return ResponseEntity.ok(
                cartService.updateQuantity(cartItemId, quantity)
        );
    }
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Long cartItemId) {

        String message = cartService.removeItem(cartItemId);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<String> calculateCartTotal(
            @PathVariable Long userId) {

        double total = cartService.calculateCartTotal(userId);

        return ResponseEntity.ok("Cart Total :"+total);
    }
}
