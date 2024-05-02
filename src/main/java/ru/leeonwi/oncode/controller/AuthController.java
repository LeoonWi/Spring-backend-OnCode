package ru.leeonwi.oncode.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.leeonwi.oncode.model.User;
import ru.leeonwi.oncode.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("registration")
    public Map<String, String> registration(@RequestBody User user) {
        Map<String, String> result = new HashMap<>();
        if (!userService.createUser(user)) {
            result.put("message", "Email already!");
            return result;
        }
        result.put("message", "Done!");
        return result;
    }

    @PostMapping("login")
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        return userService.loginUser(payload.get("email"), payload.get("password"));
    }
}
