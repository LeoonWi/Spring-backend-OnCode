package ru.leeonwi.oncode.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.leeonwi.oncode.model.User;
import ru.leeonwi.oncode.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService service;

    @GetMapping("findByEmail")
    public List<Map<String, Object>> getAllByEmail(@RequestParam String email) {
        return service.findAllByEmail(email);
    }

    @DeleteMapping("deleteUser")
    public void deleteUser(@RequestParam String email) {
        service.deleteUser(email);
    }
}
