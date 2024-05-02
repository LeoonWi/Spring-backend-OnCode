package ru.leeonwi.oncode.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.leeonwi.oncode.model.Session;
import ru.leeonwi.oncode.model.User;
import ru.leeonwi.oncode.repository.SessionRepository;
import ru.leeonwi.oncode.repository.UserRepository;

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

    public List<Resource> syncFiles(Long session_id) {
        List<Resource> files = new ArrayList<>();

        try {
            Path folderPath = Paths.get(sessionRepository.findPathToFolderById(session_id));

            // Получение списка файлов в каталоге
            Files.list(folderPath)
                    .forEach(filePath -> {
                        try {
                            // Преобразование каждого файла в объект Resource
                            Resource resource = new UrlResource(filePath.toUri());
                            if (resource.exists() && resource.isReadable()) {
                                files.add(resource);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return files;
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
