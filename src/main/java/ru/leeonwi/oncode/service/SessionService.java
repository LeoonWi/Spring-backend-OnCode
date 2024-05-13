package ru.leeonwi.oncode.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.leeonwi.oncode.model.Session;
import ru.leeonwi.oncode.model.User;
import ru.leeonwi.oncode.repository.SessionRepository;
import ru.leeonwi.oncode.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@AllArgsConstructor
@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public void updateFile(Long session_id, String text) {
        Session session = sessionRepository.getById(session_id);
        String folderPath = session.getPathToFolder();
        try(FileWriter writer = new FileWriter(folderPath, false)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> downloadFile(Long session_id) {
        Session session = sessionRepository.getById(session_id);
        String folderPath = session.getPathToFolder();
        Map<String, String> result = new HashMap<>();
        StringBuffer stringBuffer = new StringBuffer();
        try(BufferedReader br = new BufferedReader(new FileReader(folderPath))) {
            String line;
            while((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("string", stringBuffer.toString());

        return result;
    }

    public void addFriend(Long session_id, Long friend_id) {
        Session session = sessionRepository.getById(session_id);
        User user = userRepository.getById(friend_id);
        List<User> member = session.getMembers();
        member.add(user);
        session.setMembers(member);
        sessionRepository.save(session);
    }

    public Map<String, Object> getSessionByUser(Long user_id) {
        Map<String, Object> result = new HashMap<>();
        Session session = sessionRepository.findSessionsByUserId(user_id);
        if (session != null) {
            result.put("session_member_id", session.getId());
        } else {
            result.put("message", "Null");
        }
        return result;
    }
}
