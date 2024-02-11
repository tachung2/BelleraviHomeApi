package kr.co.belleravi.homeapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import kr.co.belleravi.homeapi.dto.NewsDTO;
import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ImgBBService imgBBService;

    public News getNewsById(Long newsId) {
        return newsRepository.findById(newsId).orElse(null);
    }

    public News createNews(NewsDTO newsDTO) throws IOException {
        // Imgbb로 api키와 이미지를 보내야 함

        String imgUrl = imgBBService.uploadImage(newsDTO.getImgFile());

        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setImgPath(imgUrl); // 이미지 URL 저장
        news.setContent(newsDTO.getContent());
        news.setTag(newsDTO.getTag());
        news.setDate(LocalDateTime.now());

        return newsRepository.save(news);
    }

    public void updateNews(Long newsId, NewsDTO newsDTO) throws IOException {
        // newsId를 사용하여 기존 뉴스 정보를 가져온다
        News existingNews = getNewsById(newsId);

        if (existingNews != null) {
            // 기존 뉴스 정보 업데이트
            existingNews.setTitle(newsDTO.getTitle());
            existingNews.setTag(newsDTO.getTag());
            existingNews.setContent(newsDTO.getContent());

            // 이미지가 업데이트되었을 경우에만 처리
            if (newsDTO.getImgFile() != null && !newsDTO.getImgFile().isEmpty()) {
                String imgUrl = imgBBService.uploadImage(newsDTO.getImgFile());
                existingNews.setImgPath(imgUrl);
            }

            // 수정된 뉴스 정보 저장
            newsRepository.save(existingNews);
        }
    }

    public void deleteNews(Long newsId) {
        // 뉴스 삭제 로직 구현
        newsRepository.deleteById(newsId);
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
}
