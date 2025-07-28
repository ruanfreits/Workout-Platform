package com.gym_project.projeto_bulkhouse.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "exercises") 
public class Exercise{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseId;

    private String exerciseName;
    private Integer series;
    private Integer repeticoes;
    private String exerciseDescription;
    private String imageName;


    public Exercise(){
    }

    public Exercise(String exerciseName,Integer series, Integer repeticoes ,String exerciseDescription,String imageName){
        this.exerciseName = exerciseName;
        this.series = series;
        this.repeticoes = repeticoes;
        this.exerciseDescription = exerciseDescription;
        this.imageName = imageName;
    }

    public Long getId() {
        return exerciseId;
    }

    public void setId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getseries(){
        return series;
    }

    public void setSeries(Integer series){
        this.series = series;
    }

    public Integer getrepeticoes(){
        return repeticoes;
    }

    public void setRepeticoes(Integer repeticoes){
        this.repeticoes = repeticoes;
    }
    
    
    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


}
