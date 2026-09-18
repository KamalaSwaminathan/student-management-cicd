package com.kamala.studentmanagement.service;

import com.kamala.studentmanagement.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    @Test
    void addStudent_assignsIdAndStoresRecord() {
        Student student = new Student(null, "Kamala", "B.Tech CSE");

        Student saved = studentService.addStudent(student);

        assertNotNull(saved.getId());
        assertEquals("Kamala", saved.getName());
        assertEquals("B.Tech CSE", saved.getCourse());
    }

    @Test
    void getAllStudents_returnsEveryAddedRecord() {
        studentService.addStudent(new Student(null, "Kamala", "B.Tech CSE"));
        studentService.addStudent(new Student(null, "Arjun", "B.Tech ECE"));

        Collection<Student> all = studentService.getAllStudents();

        assertEquals(2, all.size());
    }

    @Test
    void getStudentById_returnsEmptyWhenNotFound() {
        assertTrue(studentService.getStudentById(999L).isEmpty());
    }
}
