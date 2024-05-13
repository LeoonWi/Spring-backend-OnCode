package ru.leeonwi.oncode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.leeonwi.oncode.model.Session;
import ru.leeonwi.oncode.model.User;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    User findOwnerById(Long id);
    Session findByOwner(User user);
    Session getById(Long id);

    @Query("SELECT s FROM Session s JOIN s.members m WHERE m.id = :userId")
    Session findSessionsByUserId(Long userId);
}
