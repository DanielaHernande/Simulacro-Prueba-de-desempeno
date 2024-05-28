package com.riwi.Simulacro_Spring_Boot.domain.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "submission")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    @Temporal(TemporalType.DATE)
    private Date submissionDate;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal grade;

    // User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    // Assignment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", referencedColumnName = "id")
    private Assignment assignment;
}
