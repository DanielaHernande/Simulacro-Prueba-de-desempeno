package com.riwi.Simulacro_Spring_Boot.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

    // Para buscar por el nombre del curso
    //@Query(value = "select c from courses c where s.name like :courseName")
    public List<Course> findByCourseNameContaining(String courseName);

}

// https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
