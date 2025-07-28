package com.gym_project.projeto_bulkhouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym_project.projeto_bulkhouse.Model.Exercise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    
    //List<Exercise> findByExerciseName(String exercisename);
    
    

    @Query(
     value="SELECT case when count(c) >0 then true ELSE false END FROM Exercise c WHERE c.exerciseName = :exerciseName")
    Boolean existExerciseByName(@Param("exerciseName") String exerciseName);
    
    
    
    // @Query(value = "SELECT exercisename FROM Exercise WHERE exercisename = :exercisename") 
    // String existExerciseByName(@Param("exercisename") String exercisename);
}
