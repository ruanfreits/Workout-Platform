package com.gym_project.projeto_bulkhouse.Dtos;

public class AuthenticatedAlunoDto {
    private Long alunoId;
    private String email;
    private String nome;
    private Integer idade;
    private String altura;
    private String telefone;
    private String imagename;
    

    public AuthenticatedAlunoDto(Long alunoId, String email, String nome, Integer idade, String altura, String telefone, String imagename) {
        this.alunoId = alunoId;
        this.email = email;
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.telefone = telefone;
        this.imagename = imagename;
    }

    public AuthenticatedAlunoDto(){

    };

    public Long getAlunoId(){
        return alunoId;
    }
    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade(){
        return idade;
    }

    public String getAltura(){
        return altura;
    }

    public String getTelefone(){
        return telefone;
    }
    public String getImageName(){
        return imagename;
    }

}
