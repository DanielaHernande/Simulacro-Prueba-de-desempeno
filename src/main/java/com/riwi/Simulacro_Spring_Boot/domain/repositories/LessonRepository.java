package com.riwi.Simulacro_Spring_Boot.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.Simulacro_Spring_Boot.domain.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{
    
}
