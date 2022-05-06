package com.example.login_register_demo_spring.controller;

import com.example.login_register_demo_spring.events.ChangePasswordEvent;
import com.example.login_register_demo_spring.events.OnCreateAccountEvent;
import com.example.login_register_demo_spring.models.Account;
import com.example.login_register_demo_spring.models.Password;
import com.example.login_register_demo_spring.models.RegisterUserDTO;
import com.example.login_register_demo_spring.models.ResetToken;
import com.example.login_register_demo_spring.repository.ResetTokenRepository;
import com.example.login_register_demo_spring.repository.RoleRepository;
import com.example.login_register_demo_spring.service.AccountService;
import com.example.login_register_demo_spring.service.ResetTokenService;
import com.example.login_register_demo_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController  {

    @Autowired
    private UserService userService;
@Autowired
private ApplicationEventPublisher publisher;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResetTokenService resetTokenService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @GetMapping("/login")
    public String getLoingPage(@RequestParam(defaultValue = "false") boolean error,Model model)

    {
        if(error)model.addAttribute("error","Cant find user");
       else  model.addAttribute("error","");
        model.addAttribute("Logged",false);
        return "login";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("Logged",true);

        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("user",new RegisterUserDTO());
        model.addAttribute("Logged",false);
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterUserDTO dto){
        //
        // User user=new User(UUID.randomUUID(),dto.getFirstName(),dto.getLastName(),dto.getEmail(),encoder.encode(dto.getPassword()), dto.getRole());
      Account account=new Account(dto.getEmail(),dto.getFirstName(),dto.getLastName(),encoder.encode(dto.getPassword()));

      accountService.CreateAccount(account);
     //  boolean success= userService.save(user);
       //send event
        publisher.publishEvent(new OnCreateAccountEvent(account,"localhost:8080"));

        return "redirect:/login";
    }

    @GetMapping("/accountConfirm")
    public String ConfirmAcc(@RequestParam("token") String token){
        accountService.ConfirmAccount(token);
        //vrati view;
        return "login";
    }

    @GetMapping("/resetPassword")
    public String resetPasswordPage(Model model){
        model.addAttribute("password",new Password());
        return "resetPassword";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("password") Password password, BindingResult result){



        publisher.publishEvent(new ChangePasswordEvent(password,""));

        return "redirect:/login";
    }
    @GetMapping("/resetComplete")
    public String resetPasswordSuccess(@RequestParam("token") String token,Model model) {
        if (resetTokenService.confirmResetToken(token))
            {
                Password password = new Password();
                password.setToken(token);
                model.addAttribute("password", password);
                return "updatePassword";
            }
            return "login";

    }

    @PostMapping("/resetPasswordSuccess")
    public String resetPasswordSuccess(@ModelAttribute("password") Password password, @RequestParam("token") String token,BindingResult result){

        //password and password repat should match
        //check toke
password.setToken(token);
 resetTokenService.update(password);

        return "redirect:/login";
    }
}
