package ru.leeonwi.oncode.controller;

import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.leeonwi.oncode.service.SessionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/sessions")
@AllArgsConstructor
public class SessionController {
    private SessionService service;

    @PostMapping("updateFile")
    public void updateFile(@RequestBody Map<String, Object> payload) {
        service.updateFile(
                Long.parseLong(String.valueOf(payload.get("session_id"))),
                String.valueOf(payload.get("text"))
        );
    }

    @GetMapping("downloadFile")
    public Map<String, String> downloadFile(@RequestParam Long session_id) {
        return service.downloadFile(session_id);
    }

    @PostMapping("addFriendToSession")
    public void addFriendToSession(@RequestBody Map<String, Long> payload) {
        service.addFriend(payload.get("session_id"), payload.get("user_id"));
    }

    @PostMapping("getOtherSession")
    public Map<String,Object> getOtherSession(@RequestBody Map<String, Long> payload) {
        return service.getSessionByUser(payload.get("user_id"));
    }
}
