package com.example.QAPP.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) = LOWER(:name) AND s.password = :password")
    Optional<Student> findByNameAndPassword(@Param("name") String name, @Param("password") String password);

}
