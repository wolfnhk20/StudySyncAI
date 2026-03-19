package org.wolf.repository;

import org.wolf.model.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long> {
    List<StudyPlan> findBySubjectId(Long subjectId);
    List<StudyPlan> findByStatus(String status);
    List<StudyPlan> findBySubjectIdAndStatus(Long subjectId, String status);
}
