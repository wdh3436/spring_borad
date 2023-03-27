package com.daehan.board.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.daehan.board.dto.AccountDto;
import com.daehan.board.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final AccountService accountService;	
	
	@GetMapping("/home")
    public String home(Model model){
        log.info("home controller");
        return "/home";
    }
	
	@GetMapping("/user")
    public String dispUser(Model model){
        log.info("home controller");
        return "/user/user";
    }
    @GetMapping("/manager")
    public String dispManager(Model model){
        log.info("home controller");
        return "/user/manager";
    }
    @GetMapping("/admin")
    public String dispAdmin(Model model){
        log.info("home controller");
        return "/user/admin";
    }
    

    @GetMapping("/loginUser")
    public String createAccountDto(Model model){
        model.addAttribute("userForm",new AccountDto());
        return "user/login/register";
    }

    @PostMapping("/loginUser")
    public String createUser(@Valid AccountDto Dto, BindingResult result){
        if(result.hasErrors()){
            return "user/login/register";
        }
        accountService.createUser(Dto);

        return "redirect:/home";
    }

}
