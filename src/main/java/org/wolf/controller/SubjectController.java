package org.wolf.controller;

import org.wolf.dto.SubjectRequest;
import org.wolf.dto.SubjectResponse;
import org.wolf.model.Subject;
import org.wolf.model.User;
import org.wolf.service.SubjectService;
import org.wolf.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final UserService userService;

    public SubjectController(SubjectService subjectService, UserService userService) {
        this.subjectService = subjectService; this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectRequest req,
                                           @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.getUserByEmail(currentUser.getUsername()).orElseThrow();
        Subject subject = new Subject(req.getName(), req.getDifficulty(), req.getDeadline(), user);
        Subject saved = subjectService.createSubject(subject, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(SubjectResponse.fromEntity(saved));
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getMySubjects(
            @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.getUserByEmail(currentUser.getUsername()).orElseThrow();
        return ResponseEntity.ok(subjectService.getSubjectsByUser(user.getId())
            .stream().map(SubjectResponse::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id)
            .map(s -> ResponseEntity.ok(SubjectResponse.fromEntity(s)))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
