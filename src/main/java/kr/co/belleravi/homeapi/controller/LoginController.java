package kr.co.belleravi.homeapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.belleravi.homeapi.dto.RegisterDTO;
import kr.co.belleravi.homeapi.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class LoginController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/")
    public String mainPage() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {

        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("username", username);

        return "dashboard";
    }

    @PostMapping("/registerProc")
    public String registerProcess(RegisterDTO registerDTO) {


        registerService.registerProc(registerDTO);


        return "redirect:/login";
    }
}