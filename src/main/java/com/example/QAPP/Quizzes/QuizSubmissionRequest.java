package com.example.QAPP.Quizzes;

import lombok.Getter;

import java.util.List;

@Getter
public class QuizSubmissionRequest {
    private Long userId;  // The user's ID
    private String category; // The quiz category
    private List<Long> questionIds; // IDs of the questions (List<Long>)
    private List<Integer> answers; // User's answers (List<Integer>)
    // Return the quiz ID
    private Long quizId;  // The ID of the quiz (new field)

    // Getters and setters...


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;  // Set the quiz ID
    }
}
