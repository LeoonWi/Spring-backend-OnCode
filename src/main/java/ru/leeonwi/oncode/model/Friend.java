package ru.leeonwi.oncode.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "listFriends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender")
    private User sender;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient")
    private User recipient;
    @NonNull
    private boolean status;
}
