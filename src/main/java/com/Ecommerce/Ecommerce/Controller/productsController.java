package com.Ecommerce.Ecommerce.Controller;

import com.Ecommerce.Ecommerce.DTO.ProductDTO;
import com.Ecommerce.Ecommerce.Model.Product;
import com.Ecommerce.Ecommerce.Service.productService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class productsController {

    private final productService service;

    public productsController(productService productService) {
        this.service = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO){
        Product product = service.addProduct(productDTO);
        return new ResponseEntity<>(product, HttpStatus .CREATED);
    }

    @GetMapping("/getproducts")
    public ResponseEntity<List<Product>> getallProduct(){
        List<Product> productList=service.getallproduct();

        return ResponseEntity.ok(productList);
    }
    @GetMapping("/getproduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        Product product=service.getproduct(id);
        if(product == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product Not Found !...");
        }
        return ResponseEntity.ok(product);
    }
    @DeleteMapping("deleteproduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
       Product product= service.getproduct(id);

        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product Not Found !...");
        }
        service.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted Successfully :"+id);
    }
    @PutMapping("editproduct/{id}")
    public ResponseEntity<?> editProduct(@RequestBody ProductDTO productDTO,  @PathVariable Long id){
        Product product= service.getproduct(id);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product Not Found!...");
        }
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImageName(productDTO.getImageName());
        Product updatedProduct=service.editProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(
            @RequestParam String name) {

        return ResponseEntity.ok(
                service.searchProduct(name)
        );
    }

}
