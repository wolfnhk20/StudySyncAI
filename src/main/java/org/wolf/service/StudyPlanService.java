package org.wolf.service;

import org.wolf.model.StudyPlan;
import org.wolf.model.Subject;
import org.wolf.repository.StudyPlanRepository;
import org.wolf.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StudyPlanService {

    private final StudyPlanRepository studyPlanRepository;
    private final SubjectRepository subjectRepository;

    public StudyPlanService(StudyPlanRepository studyPlanRepository, SubjectRepository subjectRepository) {
        this.studyPlanRepository = studyPlanRepository;
        this.subjectRepository = subjectRepository;
    }

    public StudyPlan createStudyPlan(StudyPlan plan, Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new IllegalArgumentException("Subject not found: " + subjectId));
        plan.setSubject(subject);
        return studyPlanRepository.save(plan);
    }

    public List<StudyPlan> getPlansBySubject(Long subjectId) { return studyPlanRepository.findBySubjectId(subjectId); }
    public List<StudyPlan> getPlansByStatus(String status)   { return studyPlanRepository.findByStatus(status); }
    public Optional<StudyPlan> getPlanById(Long id)          { return studyPlanRepository.findById(id); }

    public StudyPlan updateStatus(Long planId, String newStatus) {
        StudyPlan plan = studyPlanRepository.findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + planId));
        plan.setStatus(newStatus);
        return studyPlanRepository.save(plan);
    }

    public void deletePlan(Long id) { studyPlanRepository.deleteById(id); }
}
