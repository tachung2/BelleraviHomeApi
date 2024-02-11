package kr.co.belleravi.homeapi.service;

import kr.co.belleravi.homeapi.dto.ProductDTO;
import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.entity.Product;
import kr.co.belleravi.homeapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImgBBService imgBBService;

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product createProduct(ProductDTO productDTO) throws IOException {
        // Imgbb로 api키와 이미지를 보내야 함

        String imgUrl = imgBBService.uploadImage(productDTO.getImgFile());

        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setImgPath(imgUrl); // 이미지 URL 저장
        product.setDescription(productDTO.getDescription());
        product.setContent(productDTO.getContent());

        return productRepository.save(product);
    }

    public void updateProduct(Long productId, ProductDTO productDTO) throws IOException {
        // newsId를 사용하여 기존 뉴스 정보를 가져온다
        Product existingProduct = getProductById(productId);

        if (existingProduct != null) {
            // 기존 뉴스 정보 업데이트
            existingProduct.setTitle(productDTO.getTitle());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setContent(productDTO.getContent());

            // 이미지가 업데이트되었을 경우에만 처리
            if (productDTO.getImgFile() != null && !productDTO.getImgFile().isEmpty()) {
                String imgUrl = imgBBService.uploadImage(productDTO.getImgFile());
                existingProduct.setImgPath(imgUrl);
            }

            // 수정된 뉴스 정보 저장
            productRepository.save(existingProduct);
        }
    }

    public void deleteProduct(Long productId) {
        // 뉴스 삭제 로직 구현
        productRepository.deleteById(productId);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
