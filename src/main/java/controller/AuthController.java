package controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import model.User;
import security.JwtUtil;
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
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
			String token = null;
			try {
				token = JwtUtil.generateToken(user.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}
            return "Bearer " + token;
        } else {
            return "Ошибка: пользователь не найден";
        }
    }
    @PostMapping("/auth/login")
    public String login() {
        return "redirect:/main";
    }
}