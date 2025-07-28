package com.gym_project.projeto_bulkhouse.Services;

import com.gym_project.projeto_bulkhouse.Model.Exercise;
import com.gym_project.projeto_bulkhouse.repository.ExerciseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.servlet.function.EntityResponse;

import com.gym_project.projeto_bulkhouse.Dtos.ExerciseDto;

import java.util.List;
import java.util.Optional;


@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository){
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> getExerciseById(Long exerciseId){
        return exerciseRepository.findById(exerciseId);
    }

    public boolean checkIfExistExercise(ExerciseDto exercise){
        if (exerciseRepository.existExerciseByName(exercise.getExerciseName()) == true ){

            //throw new RuntimeException("Already exist an exercise with this name:"+ nomeExercicio);
            return true;
        }
        else{
            return false;
        }
    }

    public Exercise addExercise(ExerciseDto input){
        Exercise exercise = new Exercise();

        exercise.setExerciseName(input.getExerciseName());
        exercise.setSeries(input.getseries());
        exercise.setRepeticoes(input.getrepeticoes());
        exercise.setExerciseDescription(input.getExerciseDescription());
        exercise.setImageName(input.getImageName());

        return exerciseRepository.save(exercise);

    }

    public void deleteExercise(Long exerciseId){
        if(exerciseRepository.existsById(exerciseId)){
            exerciseRepository.deleteById(exerciseId);
        }
        else{
            throw new RuntimeException("Exercise not found with id:"+ exerciseId);
        }
    }

    public Exercise updateExercise(Long exerciseId, Exercise exercise){
        if(exerciseRepository.existsById(exerciseId)){
            //exerciseRepository.deleteById(id);
            //String muscleworked = exercise.getMuscleWorked();
            

            return exerciseRepository.save(exercise);
        }
        else{
            throw new RuntimeException("Book not found with id:"+ exerciseId);
        }
    }

    // public void addExercise(Exercise exercise);
    // public boolean checkExercise(Exercise exercise);
}
