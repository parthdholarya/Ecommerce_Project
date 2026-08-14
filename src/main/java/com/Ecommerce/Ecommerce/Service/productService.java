package com.Ecommerce.Ecommerce.Service;

import com.Ecommerce.Ecommerce.DTO.ProductDTO;
import com.Ecommerce.Ecommerce.Model.Product;
import com.Ecommerce.Ecommerce.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productService {
    private final ProductRepository productRepository;

    public productService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductDTO productDTO){
        Product product=new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImageName(productDTO.getImageName());
        return productRepository.save(product);
    }
    public List<Product> getallproduct(){
        return productRepository.findAll();

    }
    public Product getproduct(Long id){
        return productRepository.findById(id).orElse(null);

    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public Product editProduct(Product product){
        return productRepository.save(product);
    }
}
