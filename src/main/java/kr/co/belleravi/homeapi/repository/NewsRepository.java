package kr.co.belleravi.homeapi.repository;

import kr.co.belleravi.homeapi.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

    void deleteById(Long id);
}
