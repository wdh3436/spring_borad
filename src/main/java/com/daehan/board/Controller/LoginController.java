package com.daehan.board.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daehan.board.domain.entity.AccountEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

	
	@GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model)
	{
		log.info("login controller");
		model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "user/login/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        log.info("logout controller");        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();        
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/home";
    }
    
    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception",required = false) String exception,
                               Model model){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       AccountEntity account = (AccountEntity)authentication.getPrincipal();
       model.addAttribute("username",account.getUsername());
       model.addAttribute("exception",exception);
       return "user/login/denied";
    }
}
