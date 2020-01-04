package com.example.github.controller;


import com.example.github.entity.Message;
import com.example.github.entity.User;
import com.example.github.respository.RespoitoryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private RespoitoryMessage respoitoryMessage;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String main(Map<String, Object> model){
        model.put("some", "Some");
        return "main";
    }

    @GetMapping("/messages")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name
            , Map<String, Object> model){
        Iterable<Message> messages = respoitoryMessage.findAll();
        model.put("name", name);
        model.put("messages", messages);
        return "messages";
    }

    @PostMapping("/messages")
    public String postGreeting(@AuthenticationPrincipal User user
            , @Valid Message message
            , BindingResult bindingResult
            , @RequestParam("file") MultipartFile file
            , Model model) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);

        }else {

            saveFile(message, file);
            model.addAttribute("message", null);
            respoitoryMessage.save(message);
        }
        Iterable<Message> messages = respoitoryMessage.findAll();
        model.addAttribute("messages", messages);

        return "messages";
    }


    @PostMapping("/filter")
    public String postFilter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> list;
        if (filter != null && !filter.isEmpty())
            list = respoitoryMessage.findByTag(filter);
        else
            list = respoitoryMessage.findAll();
        model.put("messages", list);
        model.put("filter", filter);
        return "messages";
    }

    @GetMapping("/user-messages/{user}")
    public String getUserMessages(@AuthenticationPrincipal User currentUser
            , @PathVariable User user
            , Model model
            , @RequestParam(required = false) Message message
    ){
        Set<Message> messages = user.getMessages();
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser
            , @PathVariable Long user
            , @RequestParam("id") Message message
            , @RequestParam("text") String text
            , @RequestParam("tag") String tag
            , @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)){
            if (!StringUtils.isEmpty(text))
                message.setText(text);
            if (!StringUtils.isEmpty(tag))
                message.setTag(tag);

            saveFile(message, file);

            respoitoryMessage.save(message);

        }
        return  "redirect:/user-messages/" + user;
    }


    private void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }

}
