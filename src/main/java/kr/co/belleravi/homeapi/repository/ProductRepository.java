package kr.co.belleravi.homeapi.repository;

import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteById(Long id);
}
