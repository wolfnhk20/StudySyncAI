package org.wolf.dto;
import org.wolf.model.Subject;
import java.time.LocalDate;

public class SubjectResponse {
    private Long id; private String name; private String difficulty;
    private LocalDate deadline; private Long userId;

    public SubjectResponse(Long id, String name, String difficulty, LocalDate deadline, Long userId) {
        this.id=id; this.name=name; this.difficulty=difficulty; this.deadline=deadline; this.userId=userId;
    }

    public static SubjectResponse fromEntity(Subject s) {
        return new SubjectResponse(s.getId(), s.getName(), s.getDifficulty(), s.getDeadline(), s.getUser().getId());
    }

    public Long getId()           { return id; }
    public String getName()       { return name; }
    public String getDifficulty() { return difficulty; }
    public LocalDate getDeadline(){ return deadline; }
    public Long getUserId()       { return userId; }
}
