package com.example.QAPP.Quizzes;

import com.example.QAPP.Questions.Questions;
import com.example.QAPP.Questions.QuestionsRepository;
import com.example.QAPP.student.Student;
import com.example.QAPP.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
@CrossOrigin(origins = "http://localhost:4200")
public class QuizzesController {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private QuizzesRepository quizzesRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Fetch 10 questions for a quiz by category
    @GetMapping("/questions/{category}")
    public ResponseEntity<List<Questions>> getQuestionsByCategory(@PathVariable String category) {
        List<Questions> questions = questionsRepository.findByCategory(category);
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build(); // If no questions are found
        }
        // Return at most 10 questions or fewer if not enough questions are available
        return ResponseEntity.ok(questions.subList(0, Math.min(10, questions.size())));
    }

    // Create a new quiz for the student (initiating the quiz)
    // Create a new quiz for the student (initiating the quiz)
    @PostMapping("/start")
    public ResponseEntity<Quizzes> startQuiz(@RequestParam Long studentId, @RequestParam String category) {
        // Fetch student from the repository
        Student student = studentRepository.findById(studentId).orElse(null);


        // Fetch questions based on the category
        List<Questions> questions = questionsRepository.findByCategory("Math");


        // Initialize quiz
        Quizzes quiz = new Quizzes();
        quiz.setCategory(category);
        quiz.setUser_id(studentId.intValue());

        List<Questions> selectedQuestions = new ArrayList<>();
        List<Long> questionIds = new ArrayList<>();
        for (Questions question : questions.subList(0, Math.min(10, questions.size()))) {
            Long questionId = question.getId();  // Get the question ID
            System.out.println("Question ID: " + questionId);  // Log the question ID
            if (questionId != null) {
                quiz.getQuestion_ids().add(questionId);
            } else {

                System.out.println("Question with null ID detected.");
            }
        }

        quiz.setQuestion_ids(questionIds);  // Set full question data
        quiz.setAnswers(new ArrayList<>(10));  // Initialize answers list based on selected questions
        quiz.setFinal_score(0);  // Initial score

        // Save quiz to repository
        quizzesRepository.save(quiz);

        // Send back the quiz with full question data (e.g., text, options, etc.)
        return ResponseEntity.ok(quiz);
    }


    // Submit quiz answers and calculate the final score
    @PostMapping("/submit")
    public ResponseEntity<Object> submitQuiz(@RequestBody QuizSubmissionRequest submissionRequest) {

        List<String> feedback = new ArrayList<>();
        int correctAnswers = 0;

        // Loop through the questions and answers provided in the request
        for (int i = 0; i < submissionRequest.getAnswers().size(); i++) {
            Long questionId = submissionRequest.getQuestionIds().get(i); // Use Long for question IDs
            int answer = submissionRequest.getAnswers().get(i); // User's answer

            // Fetch the question by ID
            Questions question = questionsRepository.findById(questionId).orElse(null);

            if (question != null) {
                // Check if the answer is correct
                if (question.getCorrect_answer() == answer) {
                    correctAnswers++;
                } else {
                    feedback.add("Question " + (i + 1) + ": Incorrect (Correct answer is " + question.getCorrect_answer() + ")");
                }
            } else {
                feedback.add("Question " + (i + 1) + ": Not found");
            }
        }

        // Calculate final score (percentage out of 10)
        int finalScore = (correctAnswers * 100) / 10;

        // Fetch the existing quiz by ID (to update it with the answers)
        Quizzes quiz = quizzesRepository.findById(submissionRequest.getQuizId()).orElse(null);

        if (quiz != null) {
            quiz.setAnswers(submissionRequest.getAnswers());

            System.out.println("Generated quizId: " + quiz.getQuiz_id());
            quizzesRepository.save(quiz);  // Save the updated quiz with answers and final score

        }

        // Return the result along with feedback
        return ResponseEntity.ok(new QuizResult(finalScore, feedback));
    }

}