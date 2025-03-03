package com.example.QAPP.Teacher;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    // Endpoint to create a new teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        // Save the teacher to the database
        Teacher createdTeacher = teacherRepository.save(teacher);
        // Return the created teacher with HTTP 201 status
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    // Endpoint to get all teachers
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        // Retrieve all teachers from the database
        List<Teacher> teachers = teacherRepository.findAll();

        // If no teachers found, return 404 not found
        if (teachers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(teachers);
        }

        // Return the list of teachers
        return ResponseEntity.ok(teachers);
    }

    // Endpoint to get a teacher by ID
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);

        // If teacher is not found, return 404 not found
        if (teacher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the teacher
        return ResponseEntity.ok(teacher.get());
    }

    // Endpoint to update a teacher
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        Optional<Teacher> teacher = teacherRepository.findById(id);

        // If teacher is not found, return 404 not found
        if (teacher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Update teacher fields
        Teacher updatedTeacher = teacher.get();
        updatedTeacher.setName(teacherDetails.getName());
        updatedTeacher.setPassword(teacherDetails.getPassword());

        // Save the updated teacher
        teacherRepository.save(updatedTeacher);

        // Return the updated teacher
        return ResponseEntity.ok(updatedTeacher);
    }

    // Endpoint to delete a teacher by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);

        // If teacher is not found, return 404 not found
        if (teacher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Delete the teacher
        teacherRepository.deleteById(id);

        // Return 204 No Content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
