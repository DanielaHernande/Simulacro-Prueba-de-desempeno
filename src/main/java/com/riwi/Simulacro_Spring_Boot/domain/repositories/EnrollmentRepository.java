package com.riwi.Simulacro_Spring_Boot.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.riwi.Simulacro_Spring_Boot.domain.entities.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

    // Para buscar un curso en el cual se puedan matricular
    @Query(value = "select e from enrollments e join fetch e.course c where c.id = :idCourse")
    Optional<Enrollment> findByCourseId(Long idCourse);
}
