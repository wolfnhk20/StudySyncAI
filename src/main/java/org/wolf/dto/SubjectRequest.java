package org.wolf.dto;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class SubjectRequest {
    @NotBlank private String name;
    private String difficulty;
    private LocalDate deadline;

    public SubjectRequest() {}
    public String getName()            { return name; }
    public void setName(String n)      { this.name = n; }
    public String getDifficulty()      { return difficulty; }
    public void setDifficulty(String d){ this.difficulty = d; }
    public LocalDate getDeadline()     { return deadline; }
    public void setDeadline(LocalDate d){ this.deadline = d; }
}
