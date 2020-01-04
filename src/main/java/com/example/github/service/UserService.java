package com.example.github.service;

import com.example.github.entity.Role;
import com.example.github.entity.User;
import com.example.github.respository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private ServiceMail serviceMail;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${host.name}")
    private String hostname;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repositoryUser.findByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }

    public boolean addUser(User user){

        User userFromDB = repositoryUser.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repositoryUser.save(user);
        sendActivationCode(user);

        return true;
    }



    public boolean activateUser(String code) {
        User user = repositoryUser.findByActivationCode(code);
        if (user == null)
            return false;
        user.setActivationCode(null);
        repositoryUser.save(user);

        return true;
    }

    public Iterable<User> findAll() {
        return repositoryUser.findAll();
    }

    public void saveUser(User user, String user_name, String user_pass, Map<String, String> form) {

        user.setUsername(user_name);
        user.setPassword(user_pass);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        repositoryUser.save(user);

    }

    public void updateProfile(User user, String password, String email) {
        String emailUser = user.getEmail();
        boolean changedEmail = false;

        if (!StringUtils.isEmpty(email) && (!StringUtils.isEmpty(emailUser) || emailUser == null)){
            if (!email.equals(emailUser)){
                user.setEmail(email);
                user.setActivationCode(UUID.randomUUID().toString());
                changedEmail = true;
            }
        }
        if (!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
        }

        repositoryUser.save(user);
        if (changedEmail)
            sendActivationCode(user);
    }

    private void sendActivationCode(User user) {
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello %s \n" +
                            "Welcome to my site\n Please, visite next link for activate your account http://%s/activate/%s"
                    , user.getUsername()
                    , hostname
                    , user.getActivationCode());
            serviceMail.send(user.getEmail(), "Activation code", message);
        }
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        repositoryUser.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        repositoryUser.save(user);
    }
}
