package com.gym_project.projeto_bulkhouse.Controller;

import java.util.List;
import java.util.Optional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.example.file_storage_api.responses.LoginResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import com.gym_project.projeto_bulkhouse.Dtos.ExerciseDto;
import com.gym_project.projeto_bulkhouse.Dtos.LoginAlunoDto;
import com.gym_project.projeto_bulkhouse.Dtos.RegisterAlunoDto;
import com.gym_project.projeto_bulkhouse.Dtos.AuthenticatedAlunoDto;
import com.gym_project.projeto_bulkhouse.Model.Aluno;
import com.gym_project.projeto_bulkhouse.Model.Exercise;
import com.gym_project.projeto_bulkhouse.Responses.LoginResponse;
import com.gym_project.projeto_bulkhouse.Services.AlunoService;
import com.gym_project.projeto_bulkhouse.Services.ExerciseService;
import com.gym_project.projeto_bulkhouse.Services.JwtService;
import com.gym_project.projeto_bulkhouse.Services.LinkResetService;
// import com.mysql.cj.x.protobuf.MysqlxSession.AuthenticateContinue;
import com.gym_project.projeto_bulkhouse.Services.MailService;
import com.gym_project.projeto_bulkhouse.repository.AlunoRepository;

import org.springframework.security.core.userdetails.*;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@RestController
@RequestMapping("/auth/api/alunos")
public class AlunoController {
    @Autowired
    private final  AlunoService alunoService;
    private final  JwtService jwtService;
    private final LinkResetService resetPass;
    private final AlunoRepository alunoRepository;
    private UserDetailsService userDetailsService;

    public AlunoController(JwtService jwtService, AlunoService alunoService, LinkResetService resetPass, ExerciseService exerciseService, AlunoRepository alunoRepository){
        this.alunoService = alunoService;
        this.jwtService = jwtService;
        this.resetPass = resetPass;
        this.alunoRepository = alunoRepository;
    }

    //Rota para logar e receber token
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginAlunoDto loginAlunoDto, HttpServletResponse response){
        String jsonNoUser = "{ \"userExist\": \"false\"}";
        //String jsonLoginNotAuthorized = "{ \"userExist\": \"true\", \"message\": \"Login Failed \"}";

        System.out.println(loginAlunoDto);
        
        if(alunoService.checkIfExistAluno(loginAlunoDto.getemail()) == false){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonNoUser) ;
                }
                    Aluno authenticatedAluno = alunoService.authenticate(loginAlunoDto);

                    String jwtToken = jwtService.generateToken(authenticatedAluno);
            
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setToken(jwtToken);
                    loginResponse.setExpiresIn(jwtService.getExpirationTime());


                    Cookie cookieToken = new Cookie("token", jwtToken );
                    cookieToken.setHttpOnly(true);
                    cookieToken.setSecure(false);
                    cookieToken.setPath("/");
                    cookieToken.setMaxAge(60*60);
                    response.addCookie(cookieToken);

                    AuthenticatedAlunoDto resposta = new AuthenticatedAlunoDto(
                        
                    authenticatedAluno.getId(),
                    authenticatedAluno.getEmailAluno(),
                    authenticatedAluno.getNameAluno(),
                    authenticatedAluno.getIdadeAluno(),
                    authenticatedAluno.getAlturaAluno(),
                    authenticatedAluno.getTelefoneAluno(),
                    authenticatedAluno.getImageName()
                    );

                    
                    return ResponseEntity.ok().body(resposta);
                // }
                // else{
                //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonLoginNotAuthorized);
                // }    
    }

    //Rota para deslogar e excluir token
    @PostMapping("/logout")
    public ResponseEntity<?> Signout(HttpServletResponse response){
        Cookie cookieToken = new Cookie("token", "null");
        cookieToken.setHttpOnly(true);
        cookieToken.setSecure(false); // Use true se estiver em HTTPS
        cookieToken.setPath("/"); // O mesmo path usado ao criar o cookie
        cookieToken.setMaxAge(60*60); // Expira imediatamente

        // Adiciona o cookie na resposta
        response.addCookie(cookieToken);

        return ResponseEntity.ok().body("{\"message\":\"logout successfully}\"}");
    }



    //Aqui é feito o Post para criação do Aluno
    @PostMapping("/signup")
    public ResponseEntity<?> createAluno(@Validated @RequestBody RegisterAlunoDto input){
        //Remover esse json futuramente
        String json = "{ \"userAlreadyexist\": \"true\"}";
        
        if(alunoService.checkIfExistAluno(input.getemail()) == true){

            return ResponseEntity.status(HttpStatus.CONFLICT).body(json) ;
        
        }
        Aluno newAluno = alunoService.addAluno(input);

        return ResponseEntity.status(HttpStatus.OK).body("{\"userAlreadyexist\": \"false\"}");
    }

    //Aqui é feito o Delete para excluir Aluno
    @DeleteMapping("/{AlunoId}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long AlunoId) throws Exception{
        alunoService.deleteAluno(AlunoId);
        return ResponseEntity.noContent().build();
    }

    //Rota para Atualizar Aluno
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateAluno(@PathVariable Long id,@Validated @RequestBody RegisterAlunoDto input){
        
        Aluno aluno = alunoRepository.findById(id).get();
        String json = "{ \"message\": \"Esse email já esta sendo utilizado\"}";
        
        System.out.println("Email do aluno aqui "+ aluno.getEmailAluno());


            alunoService.updateAluno(id, input);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"atualização feita com sucesso\"}");



        //FUNÇÃO FUTURA QUE VALIDA SE O EMAIL QUE VOCE QUER ATUALIZAR NÃO ESTA JÁ SEDO UTILIZADO POR OUTRO USUÁRIO
        // if(aluno.getEmailAluno().equals(input.getemail())){
            
        //     alunoService.updateAluno(id, input);
        //     return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"atualização feita com sucesso\"}");
        // }
        // else if(alunoService.checkIfExistAluno(input.getemail()) == false){
            
        //     alunoService.updateAluno(id, input);
        //     return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"atualização feita com sucesso\"}");
        // }else{
        //     return ResponseEntity.status(HttpStatus.CONFLICT).body(json);
        // }
        
    }


    //Rota que envia email para recuperação de Senha
    @PutMapping("/recoverpassword/{email}")
    public ResponseEntity<?> updatePasswordByEmail(@PathVariable String email) throws Exception{
        
        if(alunoService.checkIfExistAluno(email) == true){
            String Linkgerado = alunoService.sendEmailChangePassword(email);
            String json = "{ \"userAlreadyexist\": \"true\", \"linkGerado\": \""+Linkgerado+"\"}";
            //System.out.println(retornoEmail+"<-- mensagem do service");
            //alunoService.sendEmailChangePassword(email);
            return ResponseEntity.status(HttpStatus.OK).body(json) ;
        }
        else{

        }
       return ResponseEntity.status(HttpStatus.OK).body("{\"userAlreadyexist\": \"false\"}");
    }

    //Request que faz a validação do Token
    @GetMapping("/setpassword/{tokenResetPass}")
    public ResponseEntity<?> validateResetToken(@PathVariable String tokenResetPass) throws Exception{
        try{
            boolean isValid = resetPass.readToken(tokenResetPass);
            if(!isValid){
                return ResponseEntity.status(498).body("{\"Message\": \"Token isn't valid\"}");
            }
            return ResponseEntity.status(HttpStatus.OK).body("{\"Message\": \"Token is still valid\"}");
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error ao processar o token\"}");
        }
        }

    //Rota que usuário utiliza para determinar a nova senha
    @PutMapping("/setpassword/{newPassword}/{tokenResetPass}")
    public ResponseEntity<?> setNewPassword(@PathVariable String newPassword, @PathVariable String tokenResetPass)throws Exception{
        String json = "{ \"message\": \"password updated Successfully\"}";
        try{    
        boolean isValid = resetPass.readToken(tokenResetPass);
        if(!isValid){
            return ResponseEntity.status(498).body("{\"Message\": \"Token isn't valid\"}");
        }

        String emailInsideToken = resetPass.readPublicTokenInfo(tokenResetPass);
               
        alunoService.updatePasswordByRecover(emailInsideToken,newPassword);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error ao processar o token\"}");
        }}

    //Rota para Obter informações de todos os alunos
    @GetMapping
    public List<Aluno> getAllalunos(){
        return alunoService.getAllAlunos();
    }

    // @GetMapping("/validate-token")
    // public ResponseEntity<String> validateToken(HttpServletRequest request) {
    //     String token = null;

    //     // Busca o cookie chamado "token"
    //     if (request.getCookies() != null) {
    //         for (Cookie cookie : request.getCookies()) {
    //             if ("token".equals(cookie.getName())) {
    //                 token = cookie.getValue();
    //                 break;
    //             }
    //         }
    //     }

    //     if (token == null) {
    //         return ResponseEntity.status(400).body("Token não encontrado nos cookies");
    //     }

    //     try {
    //         // 🔐 Extrai o username do token (normalmente o email)
    //         String username = jwtService.extractUsername(token);

    //         // 🔍 Busca os dados do usuário (UserDetails) com base no username
    //         UserDetails userDetails = UserDetails.loadUserByUsername(username);

    //         // ✅ Valida o token com os dados do usuário
    //         boolean isValid = jwtService.isTokenValid(token, userDetails);

    //         if (isValid) {
    //             return ResponseEntity.ok("Token válido");
    //         } else {
    //             return ResponseEntity.status(401).body("Token inválido");
    //         }
    //     } catch (Exception e) {
    //         return ResponseEntity.status(401).body("Erro ao validar token: " + e.getMessage());
    //     }
    // }


    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(HttpServletRequest request) {
        try {
            // 1. Extrair token do cookie (assumindo que o cookie se chama "token")
            String token = null;
            if(request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token não encontrado");
            }

            // 2. Extrair username do token
            String username = jwtService.extractUsername(token);

            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }

            System.out.println(username);
            // 3. Carregar UserDetails pelo username
            // UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 4. Validar token
            boolean isValid = jwtService.isTokenValid(token);

            if (!isValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado ou inválido");
            }

            // 5. Token válido
            return ResponseEntity.ok("Token válido");

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro na validação do token");
        }
    }




    // @GetMapping("/me")
    // public ResponseEntity<Aluno> authenticatedUser() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    //     String email = (String) authentication.getPrincipal(); // agora é seguro
    
    //     Optional<Aluno> alunoOpt = alunoRepository.findByEmail(email);
    //     if (alunoOpt.isPresent()) {
    //         return ResponseEntity.ok(alunoOpt.get());
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    //     }
    // }
}
    // //Rota para Obter informações do Aluno pelo ID
    // @GetMapping("/{id}")
    // public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id) {
    //     Optional<Aluno> aluno = alunoService.getAlunoById(id);
    //     return aluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }