package com.example.QAPP.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private Double average = 0.0; // Default value for average
    private Integer nrtests=0;

    public void updateAverage(Double newScore) {
        this.nrtests = this.nrtests + 1; // Increment test count
        this.average = ((this.average * (this.nrtests - 1)) + newScore) / this.nrtests; // Update average
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Integer getNrtests() {
        return nrtests;
    }

    public void setNrtests(Integer nrtests) {
        this.nrtests = nrtests;
    }
}
