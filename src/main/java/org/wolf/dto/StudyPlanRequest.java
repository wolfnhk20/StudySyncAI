package org.wolf.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class StudyPlanRequest {
    @NotNull private LocalDate date;
    @Positive private Integer duration;
    private String status = "PENDING";

    public StudyPlanRequest() {}
    public LocalDate getDate()          { return date; }
    public void setDate(LocalDate d)    { this.date = d; }
    public Integer getDuration()        { return duration; }
    public void setDuration(Integer d)  { this.duration = d; }
    public String getStatus()           { return status; }
    public void setStatus(String s)     { this.status = s; }
}
