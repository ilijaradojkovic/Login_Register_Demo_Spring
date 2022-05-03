package com.example.login_register_demo_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class MainController  {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @GetMapping("/login")
    public String getLoingPage(@RequestParam(defaultValue = "false") boolean error,Model model)

    {
        if(error)model.addAttribute("error","Cant find user");
       else  model.addAttribute("error","");
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("user",new RegisterUserDTO());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterUserDTO dto){
        User user=new User(UUID.randomUUID(),dto.getFirstName(),dto.getLastName(),dto.getEmail(),encoder.encode(dto.getPassword()), dto.getRole());
      System.out.println(user);
       boolean success= userService.save(user);
        return "redirect:/login";
    }


}
