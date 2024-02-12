package kr.co.belleravi.homeapi.controller;

import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.entity.Product;
import kr.co.belleravi.homeapi.service.NewsService;
import kr.co.belleravi.homeapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productlist")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> viewProduct(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
