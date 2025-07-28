package com.gym_project.projeto_bulkhouse.repository;

//import com.gym_project.projeto_bulkhouse.repository.JpaRepository;
import com.gym_project.projeto_bulkhouse.Model.Aluno;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByEmailAluno(String emailAluno);
    
   Optional<Aluno> findById(Long Alunoid);
    
    //List<Aluno> findAll();
    @Query(
        value="SELECT case when count(c) >0 then true ELSE false END FROM Aluno c WHERE c.emailAluno = :emailAluno")
       Boolean existAlunoByEmail(@Param("emailAluno") String emailAluno);


    @Query(
        value="SELECT AlunoId FROM Aluno c WHERE c.emailAluno = :emailAluno")
        Long getIdByEmail(@Param("emailAluno") String emailAluno);


    @Query(
        value="SELECT passwordAluno FROM Aluno c WHERE c.emailAluno = :emailAluno")
        String getPasswordLogin(@Param("emailAluno") String emailAluno);


    @Query(value="SELECT case when count(c) > 0 then true else false END FROM Aluno c WHERE c.emailAluno = :emailAluno AND c.passwordAluno = :passwordAluno")
    Boolean alunoLogin(@Param("emailAluno") String emailAluno, @Param("passwordAluno") String passwordAluno);
}
