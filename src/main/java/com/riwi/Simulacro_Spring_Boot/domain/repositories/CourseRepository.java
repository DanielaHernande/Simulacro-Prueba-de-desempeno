package com.riwi.Simulacro_Spring_Boot.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

    @Query(value = "select * from courses")
    public List<Course> findByFirstname();

}


// https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
