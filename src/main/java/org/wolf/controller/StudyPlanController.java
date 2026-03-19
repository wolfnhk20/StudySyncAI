package org.wolf.controller;

import org.wolf.dto.StudyPlanRequest;
import org.wolf.dto.StudyPlanResponse;
import org.wolf.model.StudyPlan;
import org.wolf.service.StudyPlanService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/study-plans")
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    public StudyPlanController(StudyPlanService studyPlanService) {
        this.studyPlanService = studyPlanService;
    }

    @PostMapping
    public ResponseEntity<?> createPlan(@RequestParam Long subjectId,
                                        @Valid @RequestBody StudyPlanRequest req) {
        StudyPlan plan = new StudyPlan(req.getDate(), req.getDuration(), req.getStatus(), null);
        StudyPlan saved = studyPlanService.createStudyPlan(plan, subjectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(StudyPlanResponse.fromEntity(saved));
    }

    @GetMapping
    public ResponseEntity<List<StudyPlanResponse>> getPlans(
            @RequestParam(required = false) Long subjectId) {
        List<StudyPlan> plans = subjectId != null
            ? studyPlanService.getPlansBySubject(subjectId)
            : studyPlanService.getPlansByStatus("PENDING");
        return ResponseEntity.ok(plans.stream().map(StudyPlanResponse::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlanById(@PathVariable Long id) {
        return studyPlanService.getPlanById(id)
            .map(p -> ResponseEntity.ok(StudyPlanResponse.fromEntity(p)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(StudyPlanResponse.fromEntity(studyPlanService.updateStatus(id, status)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        studyPlanService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
