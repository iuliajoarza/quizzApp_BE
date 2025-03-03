package com.example.QAPP.Questions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    List<Questions> findByCategory(String category);
}
