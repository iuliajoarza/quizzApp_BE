package com.example.QAPP.Quizzes;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Quizzes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quiz_id; // Changed to Long for consistency with ID types, can be changed back to int if needed

    private int user_id; // User who took the quiz

    @ElementCollection
    @Column(name = "question_ids")
    private List<Long> question_ids=new ArrayList<>(); // List of question IDs (Long to match the typical ID type)

    @ElementCollection
    @Column(name = "answers")
    private List<Integer> answers; // List of user's answers (integer)

    private Integer final_score; // Final score of the quiz

    private String category; // Category of the quiz

    // Default constructor

    public Long getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Long> getQuestion_ids() {
        return question_ids;
    }

    public void setQuestion_ids(List<Long> question_ids) {
        this.question_ids = question_ids;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public Integer getFinal_score() {
        return final_score;
    }

    public void setFinal_score(Integer final_score) {
        this.final_score = final_score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
