package ru.leeonwi.oncode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leeonwi.oncode.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    List<User> findByEmailContaining(String email);

}
