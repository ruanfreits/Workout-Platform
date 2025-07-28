package com.gym_project.projeto_bulkhouse.Services;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;

import com.gym_project.projeto_bulkhouse.Model.Aluno;
import com.gym_project.projeto_bulkhouse.repository.AlunoRepository;


import com.gym_project.projeto_bulkhouse.Dtos.LoginAlunoDto;
import com.gym_project.projeto_bulkhouse.Dtos.RegisterAlunoDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final MailService mailService;

    private final LinkResetService resetPass;

public String Host = "http://localhost/setnewpassword/";
    @Autowired
    public AlunoService(
        AlunoRepository alunoRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        MailService mailService, LinkResetService resetPass){
        this.alunoRepository = alunoRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.resetPass = resetPass;
    }

    public List<Aluno> getAllAlunos(){
        return alunoRepository.findAll();
    }

    public Optional<Aluno> getAlunoById(Long Id){
        return alunoRepository.findById(Id);
    }

    public boolean checkIfExistAluno(String email){
        if (alunoRepository.existAlunoByEmail(email) == true ){
            return true;
        }
        else{
            return false;
        }
    }


    public Aluno addAluno(RegisterAlunoDto input){
        Aluno aluno = new Aluno();

        aluno.setNameAluno(input.getname());
        aluno.setEmailAluno(input.getemail());
        aluno.setPasswordAluno(passwordEncoder.encode(input.getpassword()));
        aluno.setIdadeAluno(input.getidade());
        aluno.setAlturaAluno(input.getaltura());
        aluno.setTelefoneAluno(input.gettelefone());
        return alunoRepository.save(aluno);
    }

    public Aluno authenticate(LoginAlunoDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getemail(),
                        input.getpassword()
                )
        );

        return alunoRepository.findByEmailAluno(input.getemail()).orElseThrow();
    }



    //Antigo login
    public boolean loginAluno(String email, String password){
        
        String passwordAtual  = alunoRepository.getPasswordLogin(email);
        boolean senhaAtualValida = BCrypt.checkpw(password, passwordAtual);

        if(senhaAtualValida == true){
            return true;
        }
        else{
            return false;
        }
    }

    public void deleteAluno(Long AlunoId){
        if(alunoRepository.existsById(AlunoId)){
            alunoRepository.deleteById(AlunoId);
        }
        else{
            throw new RuntimeException("Book not found with id:"+ AlunoId);
        }
    }

    public Aluno updateAluno(Long AlunoId, RegisterAlunoDto input){
        
        
        if(alunoRepository.existsById(AlunoId)){
        
            Aluno aluno = alunoRepository.findById(AlunoId).get();
            if (input.getname() != null) aluno.setNameAluno(input.getname());
            if (input.getemail() != null) aluno.setEmailAluno(input.getemail());
            if (input.getimagename() != null)     aluno.setImageName(input.getimagename());
            if (input.getidade() != null)     aluno.setIdadeAluno(input.getidade());
            if (input.getaltura() != null)     aluno.setAlturaAluno(input.getaltura());
            if (input.gettelefone() != null)     aluno.setTelefoneAluno(input.gettelefone());
                return alunoRepository.save(aluno);
            }

        else{
            throw new RuntimeException("Aluno not found with id:"+ AlunoId);
        }
    }

    //Serviço utilizado no recoverpassword
    public String sendEmailChangePassword(String Email) throws Exception{
            
            String tokenResetPass = resetPass.createToken(Email);

            mailService.enviarEmailTexto(Email,"Troca de senha","Abaixo o Link para você atualizar sua Senha:\n "+Host+tokenResetPass+"");

            return Host.concat(tokenResetPass);
    }
    
    public void updatePasswordByRecover(String email, String newPassword){
        Aluno aluno = alunoRepository.findByEmailAluno(email).orElseThrow();

        aluno.setPasswordAluno(passwordEncoder.encode(newPassword));
        alunoRepository.save(aluno);
    
    }

    public void updatePassword(Aluno aluno){
        Long AlunoId = alunoRepository.getIdByEmail(aluno.getEmailAluno());
        aluno.setId(AlunoId);
        alunoRepository.save(aluno);
        
    }



}
