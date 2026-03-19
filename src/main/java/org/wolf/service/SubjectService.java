package org.wolf.service;

import org.wolf.model.Subject;
import org.wolf.model.User;
import org.wolf.repository.SubjectRepository;
import org.wolf.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectService(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    public Subject createSubject(Subject subject, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        subject.setUser(user);
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjectsByUser(Long userId) { return subjectRepository.findByUserId(userId); }
    public List<Subject> getAllSubjects()               { return subjectRepository.findAll(); }
    public Optional<Subject> getSubjectById(Long id)   { return subjectRepository.findById(id); }
    public void deleteSubject(Long id)                 { subjectRepository.deleteById(id); }
}
