package org.wolf.dto;
import org.wolf.model.StudyPlan;
import java.time.LocalDate;

public class StudyPlanResponse {
    private Long id; private LocalDate date; private Integer duration;
    private String status; private Long subjectId; private String subjectName;

    public StudyPlanResponse(Long id, LocalDate date, Integer duration,
                             String status, Long subjectId, String subjectName) {
        this.id=id; this.date=date; this.duration=duration;
        this.status=status; this.subjectId=subjectId; this.subjectName=subjectName;
    }

    public static StudyPlanResponse fromEntity(StudyPlan p) {
        return new StudyPlanResponse(p.getId(), p.getDate(), p.getDuration(),
            p.getStatus(), p.getSubject().getId(), p.getSubject().getName());
    }

    public Long getId()            { return id; }
    public LocalDate getDate()     { return date; }
    public Integer getDuration()   { return duration; }
    public String getStatus()      { return status; }
    public Long getSubjectId()     { return subjectId; }
    public String getSubjectName() { return subjectName; }
}
