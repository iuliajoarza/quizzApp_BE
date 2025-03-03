package com.example.QAPP.Quizzes;

import java.util.List;

public class QuizResult {
    private int score;  // Final score of the user
    private List<String> feedback;  // Feedback for each question

    public QuizResult(int score, List<String> feedback) {
        this.score = score;
        this.feedback = feedback;
    }

    // Getters and setters...

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<String> feedback) {
        this.feedback = feedback;
    }
}
