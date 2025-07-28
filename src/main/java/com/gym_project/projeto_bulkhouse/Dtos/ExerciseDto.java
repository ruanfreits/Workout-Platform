package com.gym_project.projeto_bulkhouse.Dtos;

public class ExerciseDto {
    private String exerciseName;
    private Integer series;
    private Integer repeticoes;
    private String exerciseDescription;
    private String imageName;

    public ExerciseDto(String exerciseName, String exerciseDescription, Integer series, Integer repeticoes, String imageName){
        this.exerciseName = exerciseName;
        this.series = series;
        this.repeticoes = repeticoes;
        this.exerciseDescription = exerciseDescription;
        this.imageName = imageName;
    }

    public ExerciseDto(){
    }

    public String getExerciseName(){
        return exerciseName;
    }

    public void setExerciseName(String exerciseName){
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

    public String getExerciseDescription(){
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription){
        this.exerciseDescription = exerciseDescription;
    }

    public void setImageName(String imageName){
        this.imageName = imageName;
    }

    public String getImageName(){
        return imageName;
    }
}
