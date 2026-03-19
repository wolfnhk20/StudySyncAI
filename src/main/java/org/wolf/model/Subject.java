package org.wolf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    private String difficulty;

    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Subject() {}

    public Subject(String name, String difficulty, LocalDate deadline, User user) {
        this.name = name;
        this.difficulty = difficulty;
        this.deadline = deadline;
        this.user = user;
    }

    public Long getId()                         { return id; }
    public String getName()                     { return name; }
    public void setName(String name)            { this.name = name; }
    public String getDifficulty()               { return difficulty; }
    public void setDifficulty(String d)         { this.difficulty = d; }
    public LocalDate getDeadline()              { return deadline; }
    public void setDeadline(LocalDate d)        { this.deadline = d; }
    public User getUser()                       { return user; }
    public void setUser(User user)              { this.user = user; }
}
