package ru.leeonwi.oncode.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String pathToFolder;

    @OneToOne(optional = false, mappedBy = "session")
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "member",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members = new ArrayList<>();
}
