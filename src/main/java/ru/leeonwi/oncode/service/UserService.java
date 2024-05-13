package ru.leeonwi.oncode.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leeonwi.oncode.model.Friend;
import ru.leeonwi.oncode.model.Session;
import ru.leeonwi.oncode.model.User;
import ru.leeonwi.oncode.repository.FriendRepository;
import ru.leeonwi.oncode.repository.SessionRepository;
import ru.leeonwi.oncode.repository.UserRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final FriendRepository friendRepository;

    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) return false;
        userRepository.save(user);
        boolean statusFolder = false;
        do {
            if (this.createSession(user) != false) statusFolder = true;
        } while (!statusFolder);
        userRepository.save(user);
        return true;
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Session session = sessionRepository.findByOwner(user);
            userRepository.deleteById(user.getId());
            this.deleteSession(session);
        }
    }

    private boolean createSession(User owner) {
        Session session = new Session();
        session.setOwner(owner);
        session.setPathToFolder("res/" + owner.getEmail());
        try {
            File folder = new File(session.getPathToFolder());
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (!created) return false;
                session.setPathToFolder(session.getPathToFolder() + "/File");
                Files.createFile(Path.of(session.getPathToFolder()));
            } else return false;
        } catch (Exception e) {
            return false;
        }
        owner.setSession(session);
        sessionRepository.save(session);
        return true;
    }

    private void deleteSession(Session session) {
        File folder = new File(session.getPathToFolder());
        boolean delete = deleteRecursively(folder);
        if (delete) sessionRepository.deleteById(session.getId());
        else deleteSession(session);
    }

    private static boolean deleteRecursively(File file) {   
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteRecursively(f);
                }
            }
        }
        return file.delete();
    }

    public Map<String, Object> loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            if (user.getPassword().equals(password)) {
                result.put("id", user.getId());
                result.put("email", user.getEmail());
                result.put("fullName", user.getFullName());
                result.put("nickname", user.getNickname());
                result.put("session_id", user.getSession().getId());

                return result;
            }
            result.put("message", "Неверный пароль");
            return result;
        }
        result.put("message", "Неверный email");
        return result;
    }

    public List<Map<String, Object>> findAllByEmail(String email) {
        List<User> users = userRepository.findByEmailContaining(email);
        List<Map<String, Object>> result = new ArrayList<>();
        for (User user : users) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("nickname", user.getNickname());
            userData.put("fullName", user.getFullName());
            userData.put("email", user.getEmail());

            result.add(userData);
        }
        return result;
    }

    public boolean addNewFriend(Long myId, Long hisId) {
       User me = userRepository.findById(myId).get();
       User friend = userRepository.findById(hisId).get();

       if (me != null && friend != null) {
           if (friendRepository.findFriendBySenderAndRecipient(me, friend) == null) {
               Friend newRelation = new Friend(me, friend, false);
               friendRepository.save(newRelation);
               return true;
           }
       }
       return false;
    }
}
