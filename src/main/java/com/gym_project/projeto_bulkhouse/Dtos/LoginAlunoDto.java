package com.gym_project.projeto_bulkhouse.Dtos;

public class LoginAlunoDto {
    private String email;
    private String password;
    
    public LoginAlunoDto(String email, String password){
        this.email = email;
        this.password = password;
    }
    public LoginAlunoDto(){}

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
}