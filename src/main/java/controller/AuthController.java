package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import model.User;
import service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return "Пользователь с таким именем уже существует";
        }
        if (userService.existsByEmail(user.getEmail())) {
            return "Пользователь с таким email уже зарегистрирован";
        }
        userService.saveUser(user);
        return "Регистрация успешна!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return "Функция логина будет добавлена позже!";
    }
}