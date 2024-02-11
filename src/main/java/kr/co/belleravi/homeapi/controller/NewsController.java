package kr.co.belleravi.homeapi.controller;

import kr.co.belleravi.homeapi.dto.NewsDTO;
import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/manage")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news-create")
    public String showNewsCreateForm(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("username", username);

        return "news-create";
    }

    @GetMapping("/news-edit/{newsId}")
    public String editNews(@PathVariable Long newsId, Model model) {
        // newsId를 사용하여 뉴스 정보를 가져와서 모델에 추가
        News news = newsService.getNewsById(newsId);

        if (news != null) {
            model.addAttribute("news", news);
            return "news-edit"; // 뉴스 수정 화면으로 이동
        } else {
            // 뉴스를 찾을 수 없으면 다른 페이지로 리다이렉트 또는 에러 페이지로 이동
            return "redirect:/dashboard/news";
        }
    }

    @PostMapping("/news-edit")
    public String updateNews(@RequestParam Long newsId, NewsDTO newsDTO) throws IOException {
        // 서비스 메소드 호출하여 뉴스 업데이트 로직 수행
        newsService.updateNews(newsId, newsDTO);

        // 리다이렉트 또는 다른 처리 로직을 추가
        return "redirect:/dashboard/news";
    }

    @PostMapping("/news-create")
    public String createNews(NewsDTO newsDTO) throws IOException {
        newsService.createNews(newsDTO);
        return "redirect:/dashboard/news";
    }

    @PostMapping("/news-delete")
    public String deleteNews(@RequestParam Long newsId) {
        // 뉴스 삭제 로직 수행
        newsService.deleteNews(newsId);

        // 삭제 후 리다이렉트 또는 다른 처리 로직을 추가
        return "redirect:/dashboard/news";
    }
}
