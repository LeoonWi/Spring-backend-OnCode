package ru.leeonwi.oncode.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leeonwi.oncode.model.Friend;
import ru.leeonwi.oncode.model.User;
import ru.leeonwi.oncode.repository.FriendRepository;
import ru.leeonwi.oncode.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public boolean confirmFriend(Long myId, Long hisId) {
        User sender = userRepository.findById(hisId).get();
        User recipient = userRepository.findById(myId).get();
        Friend friendship = friendRepository.findFriendBySenderAndRecipient(sender, recipient);
        if (friendship != null) {
            friendship.setStatus(true);
            friendRepository.save(friendship);
            return true;
        }
        return false;
    }

    public boolean deleteFriend(Long userId, Long friendId) {
        Friend friendship = friendRepository.findFriendByUserIds(userId, friendId);
        if (friendship != null) {
            friendRepository.delete(friendship);
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> getAllFriends(Long id) {
        List<User> users = friendRepository.findFriendsByUserId(id);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        if (!users.isEmpty()) {
            for(User user : users) {
                data.put("id", user.getId());
                data.put("email", user.getEmail());
                data.put("fullName", user.getFullName());
                data.put("nickname", user.getNickname());

                result.add(data);
            }
            return result;
        }
        data.put("message", "Empty");
        result.add(data);
        return result;
    }
    public List<Map<String, Object>> getAllInvitation(Long id) {
        List<User> users = friendRepository.findFriendsInvitationByUserId(id);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        if (!users.isEmpty()) {
            for(User user : users) {
                data.put("id", user.getId());
                data.put("email", user.getEmail());
                data.put("fullName", user.getFullName());
                data.put("nickname", user.getNickname());

                result.add(data);
            }
            return result;
        }
        data.put("message", "Empty");
        result.add(data);
        return result;
    }
}
