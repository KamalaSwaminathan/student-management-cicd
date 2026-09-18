package com.kamala.studentmanagement.service;

import com.kamala.studentmanagement.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory service for managing student records.
 * Supports the two required operations: adding and viewing records.
 */
@Service
public class StudentService {

    private final Map<Long, Student> students = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public Student addStudent(Student student) {
        long id = idGenerator.incrementAndGet();
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Optional<Student> getStudentById(Long id) {
        return Optional.ofNullable(students.get(id));
    }
}
