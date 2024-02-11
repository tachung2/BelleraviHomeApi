package kr.co.belleravi.homeapi.controller;

import kr.co.belleravi.homeapi.dto.NewsDTO;
import kr.co.belleravi.homeapi.dto.ProductDTO;
import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.entity.Product;
import kr.co.belleravi.homeapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/manage")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product-create")
    public String showProductCreateForm(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("username", username);

        return "product-create";
    }

    @GetMapping("/product-edit/{productId}")
    public String editProduct(@PathVariable Long productId, Model model) {
        // newsId를 사용하여 뉴스 정보를 가져와서 모델에 추가
        Product product = productService.getProductById(productId);

        if (product != null) {
            model.addAttribute("product", product);
            return "product-edit"; // 뉴스 수정 화면으로 이동
        } else {
            // 뉴스를 찾을 수 없으면 다른 페이지로 리다이렉트 또는 에러 페이지로 이동
            return "redirect:/dashboard/product";
        }
    }

    @PostMapping("/product-edit")
    public String updateProduct(@RequestParam Long productId, ProductDTO productDTO) throws IOException {
        // 서비스 메소드 호출하여 뉴스 업데이트 로직 수행
        productService.updateProduct(productId, productDTO);

        // 리다이렉트 또는 다른 처리 로직을 추가
        return "redirect:/dashboard/product";
    }

    @PostMapping("/product-create")
    public String createProduct(ProductDTO productDTO) throws IOException {
        productService.createProduct(productDTO);
        return "redirect:/dashboard/product";
    }

    @PostMapping("/product-delete")
    public String deleteProduct(@RequestParam Long productId) {
        // 뉴스 삭제 로직 수행
        productService.deleteProduct(productId);

        // 삭제 후 리다이렉트 또는 다른 처리 로직을 추가
        return "redirect:/dashboard/product";
    }
}
