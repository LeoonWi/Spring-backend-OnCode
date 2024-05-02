package ru.leeonwi.oncode.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.leeonwi.oncode.model.User;
import ru.leeonwi.oncode.service.FriendService;
import ru.leeonwi.oncode.service.UserService;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/friend")
public class FriendController {
    private final UserService userService;
    private final FriendService friendService;

    @PostMapping("addFriend")
    public boolean addNewFriend(@RequestBody Map<String, Long> payload) {
        return userService.addNewFriend(payload.get("myId"), payload.get("hisId"));
    }

    @PostMapping("confirmFriend")
    public boolean confirmFriend(@RequestBody Map<String, Long> payload) {
        return friendService.confirmFriend(payload.get("myId"), payload.get("hisId"));
    }

    @DeleteMapping("deleteFriend")
    public boolean deleteFriend(@RequestParam Long myId, Long hisId) {
        return friendService.deleteFriend(myId, hisId);
    }

    @GetMapping("getAllFriends")
    public List<Map<String, Object>> getAllFriends(@RequestParam Long id) {
        return friendService.getAllFriends(id);
    }
    @GetMapping("getAllInvitation")
    public List<Map<String, Object>> getAllInvitation(@RequestParam Long id) {
        return friendService.getAllInvitation(id);
    }
}
