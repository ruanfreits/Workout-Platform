package com.gym_project.projeto_bulkhouse.Controller;

import com.gym_project.projeto_bulkhouse.Model.Exercise;
import com.gym_project.projeto_bulkhouse.Dtos.ExerciseDto;

import com.gym_project.projeto_bulkhouse.Services.ExerciseService;
import com.gym_project.projeto_bulkhouse.Services.S3Service;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;
    private S3Service s3Service;

    public ExerciseController(ExerciseService exerciseService, S3Service s3Service){
        this.exerciseService = exerciseService;
        this.s3Service = s3Service;
    }

    @GetMapping
    public List<Exercise> getAllxercises(){
        return exerciseService.getAllExercises();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        Optional<Exercise> exercise = exerciseService.getExerciseById(id);
        return exercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    

    // Aqui é feito o Post para criação do Exercício
    @PostMapping
    public ResponseEntity<Object> createExercise(@RequestBody ExerciseDto exercise){
        if(exerciseService.checkIfExistExercise(exercise) == true){

            return new ResponseEntity<Object>("The Exercise Name Already Exists", HttpStatus.CONFLICT);
        }
        exerciseService.addExercise(exercise);
        return new ResponseEntity<Object>(exercise, HttpStatus.OK);
    }


    @PostMapping("/sendFile/{fileName}")
    public ResponseEntity<?> uploadExerciseImage(@PathVariable String fileName, @RequestParam("arquivo") MultipartFile arquivo) throws IOException, java.io.IOException{
        //The file is saved temporarily on the server before uploading to S3.
        String keyName = fileName;

        System.out.println(keyName +"Nome que será usado na imagem");
        File tempFile = File.createTempFile("temp", null);
        arquivo.transferTo(tempFile);
        
        s3Service.uploadFile(keyName, tempFile.getAbsolutePath());
        return new ResponseEntity<Object>(HttpStatus.OK);
}

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id){
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Exercise updateExercise(@PathVariable Long id, @RequestBody Exercise exercise){
        return exerciseService.updateExercise(id, exercise);
        //return ResponseEntity.noContent().build();
    }
}


