package kr.co.belleravi.homeapi.controller;

import kr.co.belleravi.homeapi.entity.News;
import kr.co.belleravi.homeapi.entity.Product;
import kr.co.belleravi.homeapi.service.NewsService;
import kr.co.belleravi.homeapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DashController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private ProductService productService;

    @GetMapping("/dashboard/news")
    public String news(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<News> newsList = newsService.getAllNews();

        model.addAttribute("username", username);
        model.addAttribute("newsList", newsList);

        return "news";
    }

    @GetMapping("/dashboard/product")
    public String product(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Product> productList = productService.getAllProduct();

        model.addAttribute("username", username);
        model.addAttribute("productList", productList);

        return "product";
    }


}
