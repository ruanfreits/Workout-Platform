package com.gym_project.projeto_bulkhouse.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Table(name = "alunos")
public class Aluno implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AlunoId;

    private String nameAluno;
    private String emailAluno;
    private String passwordAluno;
    private Integer idadeAluno;
    private String alturaAluno;
    private String telefoneAluno;
    private String imageName;
    
    // private Float percentualGordura;
    
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Aluno(){
    }

    public Aluno(String nameAluno, String emailAluno, String passwordAluno, Date createdAt, Date updatedAt, Integer idadeAluno, String alturaAluno, String telefoneAluno, String imageName ){
        this.nameAluno = nameAluno;
        this.emailAluno = emailAluno;
        this.passwordAluno = passwordAluno;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idadeAluno = idadeAluno;
        this.alturaAluno = alturaAluno;
        this.telefoneAluno = telefoneAluno;
        this.imageName = imageName;
    }

    public Long getId(){
        return AlunoId;
    }

    public void setId(Long AlunoId){
        this.AlunoId = AlunoId;
    }

    public String getNameAluno(){
        return nameAluno;
    }

    public void setNameAluno(String nameAluno){
        this.nameAluno = nameAluno;
    }

    public String getEmailAluno(){
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno){
        this.emailAluno = emailAluno;
    }

    public String getPasswordAluno(){
        return passwordAluno;
    }

    public void setPasswordAluno(String passwordAluno){
        this.passwordAluno = passwordAluno;
    }

    public Integer getIdadeAluno(){
        return idadeAluno;
    }

    public void setIdadeAluno(Integer idadeAluno){
        this.idadeAluno = idadeAluno;
    }

    public String getAlturaAluno(){
        return alturaAluno;
    }

    public void setAlturaAluno(String alturaAluno){
        this.alturaAluno = alturaAluno;
    }

    public String getTelefoneAluno(){
        return telefoneAluno;
    }

    public void setTelefoneAluno(String telefoneAluno){
        this.telefoneAluno = telefoneAluno;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword(){
        return passwordAluno;
    }

    @Override
    public String getUsername() {
        return emailAluno;
    }

    @Override 
    public  boolean  isAccountNonExpired () { 
        return  true ; 
    } 

    @Override 
    public  boolean  isAccountNonLocked () { 
        return  true ; 
    } 

    @Override 
    public  boolean  isCredentialsNonExpired () { 
        return  true ; 
    } 

    @Override 
    public  boolean  isEnabled () { 
        return  true ; 
    }
}
