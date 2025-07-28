package com.gym_project.projeto_bulkhouse.Dtos;

public class RegisterAlunoDto {
    private String name;
    private String email;
    private String password;
    private Integer idade;
    private String altura;
    private String telefone;
    private String imagename;


    public RegisterAlunoDto(String name, String email, String password, Integer idade, String altura, String telefone, String imagename){
        this.name = name;
        this.email = email;
        this.password = password;
        this.idade = idade;
        this.altura = altura;
        this.telefone = telefone;
        this.imagename = imagename;
    }

    public RegisterAlunoDto(){}

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
    
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }
    public String getpassword() {
        return password;
    }
    public void setpassword(String password) {
        this.password = password;
}

    public Integer getidade(){
        return idade;
    } 

    public void setidade(Integer idade){
        this.idade = idade;
    }

    public String getaltura(){
        return altura;
    }

    public void setaltura(String altura){
        this.altura = altura;
    }

    public String gettelefone(){
        return telefone;
    }
    public void settelefone(String telefone){
        this.telefone = telefone;
    }

    public void setimagename(String imagename){
        this.imagename = imagename;
    }

    public String getimagename(){
        return imagename;
    }

}
