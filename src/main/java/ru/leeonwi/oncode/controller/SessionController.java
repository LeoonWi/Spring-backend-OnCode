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

    @PostMapping("syncFiles")
    public List<Resource> syncFiles(@RequestBody Map<String, Long> payload) {
        return service.syncFiles(payload.get("session_id"));
    }

//    @PostMapping("downloadFiles")
//    public List<Resource> downloadFiles(@RequestBody )

    public void addFriendToSession(@RequestBody Map<String, Long> payload) {
        service.addFriend(payload.get("session_id"), payload.get("friend_id"));
    }

    public Map<String,Object> getOtherSession(@RequestBody Map<String, Long> payload) {
        return service.getSessionByUser(payload.get("user_id"));
    }
}
