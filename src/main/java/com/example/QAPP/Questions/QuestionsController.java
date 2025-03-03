package com.example.QAPP.Questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionsController {

    @Autowired
    private QuestionsRepository questionsRepository;

    // Endpoint to create a new question
    @PostMapping
    public ResponseEntity<Questions> createQuestion(@RequestBody Questions question) {
        // Save the question to the database
        Questions createdQuestion = questionsRepository.save(question);
        // Return the created question with HTTP 201 status
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    // Endpoint to fetch all questions
    @GetMapping
    public ResponseEntity<List<Questions>> getAllQuestions() {
        // Retrieve all questions from the database
        List<Questions> questions = questionsRepository.findAll();

        // If no questions are found, return 404 status
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(questions);
        }

        // Return the list of questions
        return ResponseEntity.ok(questions);
    }

    // Endpoint to fetch a question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Questions> getQuestionById(@PathVariable Long id) {
        // Retrieve the question by ID
        return questionsRepository.findById(id)
                .map(ResponseEntity::ok) // If found, return 200 with the question
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // If not, return 404
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Questions>> getQuestionsByCategory(@PathVariable String category) {

        List<Questions> questions = questionsRepository.findByCategory(category);

        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(questions);
        }

        return ResponseEntity.ok(questions);
    }

    // Endpoint to update an existing question
    @PutMapping("/{id}")
    public ResponseEntity<Questions> updateQuestion(@PathVariable Long id, @RequestBody Questions updatedQuestion) {
        // Check if the question exists
        Optional<Questions> existingQuestionOpt = questionsRepository.findById(id);

        if (existingQuestionOpt.isPresent()) {
            Questions existingQuestion = existingQuestionOpt.get();
            // Update the question fields
            existingQuestion.setQuestion(updatedQuestion.getQuestion());
            existingQuestion.setAnswer1(updatedQuestion.getAnswer1());
            existingQuestion.setAnswer2(updatedQuestion.getAnswer2());
            existingQuestion.setAnswer3(updatedQuestion.getAnswer3());
            existingQuestion.setAnswer4(updatedQuestion.getAnswer4());
            existingQuestion.setCorrect_answer(updatedQuestion.getCorrect_answer());
            existingQuestion.setCategory(updatedQuestion.getCategory());

            // Save and return the updated question
            Questions savedQuestion = questionsRepository.save(existingQuestion);
            return ResponseEntity.ok(savedQuestion);
        }

        // If not found, return 404 status
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Endpoint to delete a question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        // Check if the question exists
        if (questionsRepository.existsById(id)) {
            // Delete the question and return 204 status
            questionsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
