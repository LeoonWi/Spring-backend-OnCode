package ru.leeonwi.oncode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.leeonwi.oncode.model.Friend;
import ru.leeonwi.oncode.model.User;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Friend findFriendBySenderAndRecipient(User sender, User recipient);

    @Query("SELECT DISTINCT u FROM User u " +
            "INNER JOIN Friend f ON u.id = f.sender.id OR u.id = f.recipient.id " +
            "WHERE u.id != :userId AND f.status = true")
    List<User> findFriendsByUserId(Long userId);

    @Query("SELECT DISTINCT u FROM User u " +
            "INNER JOIN Friend f ON u.id = f.sender.id " +
            "WHERE u.id != :userId AND f.status = false")
    List<User> findFriendsInvitationByUserId(Long userId);

    @Query("SELECT f FROM Friend f " +
            "WHERE (f.sender.id = :userId AND f.recipient.id = :friendId) " +
            "OR (f.sender.id = :friendId AND f.recipient.id = :userId)")
    Friend findFriendByUserIds(Long userId, Long friendId);

}
