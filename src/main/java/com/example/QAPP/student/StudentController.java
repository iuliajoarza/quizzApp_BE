package com.example.QAPP.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // Endpoint to create a new student
    // Endpoint to create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // Save student to the database
        Student createdStudent = studentRepository.save(student);
        // Return the created student with HTTP 201 status
        return ResponseEntity.status(201).body(createdStudent);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        // Retrieve all students from the database
        List<Student> students = studentRepository.findAll();

        // If no students found, return 404 not found
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(students);
        }

        // Return the list of students
        return ResponseEntity.ok(students);
    }


    @GetMapping("/find")
    public ResponseEntity<Student> findStudentByNameAndPassword(
            @RequestParam String name,
            @RequestParam String password) {
        System.out.println("Received request with name: " + name + " and password: " + password);

        Optional<Student> student = studentRepository.findByNameAndPassword(name, password);

        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if not found
        }

        return ResponseEntity.ok(student.get()); // Return the found student
    }

    @PostMapping("/{studentId}/update-average")
    public ResponseEntity<Void> updateStudentAverage(
            @PathVariable Long studentId,
            @RequestBody Double newScore) {
        // Fetch student by ID
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if student not found
        }

        Student student = optionalStudent.get();
        // Update the student's average
        student.updateAverage(newScore);
        // Save the updated student record
        studentRepository.save(student);

        return ResponseEntity.ok().build(); // Return 200 OK
    }

    @GetMapping("/averages")
    public ResponseEntity<List<Student>> getAllStudentsWithAverages() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(students);
        }

        // Sort students by average score in descending order
        students.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));

        return ResponseEntity.ok(students);
    }
}


