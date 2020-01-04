package com.example.github.controller;


import com.example.github.entity.User;
import com.example.github.entity.json.CaptchaResponse;
import com.example.github.respository.RepositoryUser;
import com.example.github.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistController {

    private static final String captcha_url = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String recaptcha_secret_key;

    @GetMapping("/registration")
    public String Registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String PostRegistration(
             @RequestParam("password2") String passwordConfirma
            , @RequestParam(value = "g-recaptcha-response", defaultValue = "") String recaptcha_response
            , @Valid User user
            , BindingResult bindingResult
            , Model model){
        boolean confirmPass = StringUtils.isEmpty(passwordConfirma);

        if (StringUtils.isEmpty(recaptcha_response))
            model.addAttribute("mess", "\nValue of recaptcha is missing");

        String url = String.format(captcha_url, recaptcha_secret_key, recaptcha_response);
        CaptchaResponse captchaResponse = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if (!captchaResponse.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        if (confirmPass){
            model.addAttribute("password2Error", "Please fill confirm password");
        }

        if (!StringUtils.isEmpty(user.getPassword()) && !user.getPassword().equals(passwordConfirma)){
            model.addAttribute("passwordError", "Password are deifferent!");
            confirmPass = true;
        }

        if (repositoryUser.existsUserByEmail(user.getEmail())){
            model.addAttribute("emailError", "Email exist");
            confirmPass = true;
        }

        if (confirmPass || bindingResult.hasErrors() || !captchaResponse.isSuccess()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("user", user);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exist!");
            return "/registration";
        }
        model.addAttribute("mess", "User not exist!");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){

        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messType", "success");
            model.addAttribute("mess", "User successfuly activated");
        }else {
            model.addAttribute("messType", "danger");
            model.addAttribute("mess", "Activation code is not found!");
        }
        return "login";
    }


}
