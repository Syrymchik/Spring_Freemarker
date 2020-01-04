package com.example.github.controller;

import com.example.github.entity.Role;
import com.example.github.entity.User;
import com.example.github.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String GetUserList(Map<String, Object> model){
        model.put("userlist", userService.findAll());
        return "userlist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{userid}")
    public String GetUserById(@PathVariable User userid, Model model){
//        User user = repositoryUser.findById(userid);
        model.addAttribute("user", userid);
        model.addAttribute("roles", Role.values());
        return "user_edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam("user_id") User user
            , @RequestParam String user_name
            , @RequestParam String user_pass
            , @RequestParam Map<String, String> form
    ){
        userService.saveUser(user, user_name, user_pass, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String postProfile(
            @AuthenticationPrincipal User user
        , @RequestParam String password
        , @RequestParam String email
            , Model model
    ){
        userService.updateProfile(user, password, email);
        model.addAttribute("mess", "User profile was updated");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @GetMapping("subscribe/{user}")
    public String subscribeGet(
            @AuthenticationPrincipal User currentUser
            , @PathVariable User user){
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribeGet(
            @AuthenticationPrincipal User currentUser
            , @PathVariable User user){
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            Model model
            , @PathVariable User user
            , @PathVariable String type
    ){
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if ("subscriptions".equals(type))
            model.addAttribute("users", user.getSubscriptions());
        else
            model.addAttribute("users", user.getSubscribers());
        return "subcriptions";
    }



} 
