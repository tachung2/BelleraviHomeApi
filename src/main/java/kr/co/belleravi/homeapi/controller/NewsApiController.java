package kr.co.belleravi.homeapi.controller;

import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NewsApiController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/newslist")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<News> viewNews(@PathVariable Long newsId) {
        News news = newsService.getNewsById(newsId);

        if (news != null) {
            return ResponseEntity.ok().body(news);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
