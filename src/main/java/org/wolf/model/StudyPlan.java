package org.wolf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "study_plans")
public class StudyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @Positive
    @Column(nullable = false)
    private Integer duration;  // minutes

    @Column(length = 20)
    private String status = "PENDING";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    protected StudyPlan() {}

    public StudyPlan(LocalDate date, Integer duration, String status, Subject subject) {
        this.date = date;
        this.duration = duration;
        this.status = status;
        this.subject = subject;
    }

    public Long getId()                      { return id; }
    public LocalDate getDate()               { return date; }
    public void setDate(LocalDate date)      { this.date = date; }
    public Integer getDuration()             { return duration; }
    public void setDuration(Integer d)       { this.duration = d; }
    public String getStatus()                { return status; }
    public void setStatus(String status)     { this.status = status; }
    public Subject getSubject()              { return subject; }
    public void setSubject(Subject subject)  { this.subject = subject; }
}
