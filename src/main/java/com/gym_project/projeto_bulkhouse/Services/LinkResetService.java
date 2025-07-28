package com.gym_project.projeto_bulkhouse.Services;

import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.stereotype.Service;

import org.springframework.security.core.token.Token;

import java.util.Base64;
import java.util.Date;
import java.util.Random;

@Service
public class LinkResetService {
    

    public String createToken (String email) throws Exception{
        KeyBasedPersistenceTokenService service = new KeyBasedPersistenceTokenService();
        
        //Verificar futuramente se no Server Secret é utilizado a senha atual do usuário.
        service.setServerSecret("SECRET123");
        service.setServerInteger(16);
        service.setSecureRandom(new SecureRandomFactoryBean().getObject());

        Token token = service.allocateToken(email);

        // System.out.println(token.getExtendedInformation());
        // System.out.println(new Date(token.getKeyCreationTime()));
        // System.out.println(token.getKey());
        return token.getKey();

    }

    public boolean readToken(String rawToken) throws Exception{
    try{
        KeyBasedPersistenceTokenService service = new KeyBasedPersistenceTokenService();
        service.setServerSecret("SECRET123");
        service.setServerInteger(16);
        service.setSecureRandom(new SecureRandomFactoryBean().getObject());
        
        Token token = service.verifyToken(rawToken);
    return true;
    }
    catch (Exception ex){
        System.err.println("Error ao validar o token: "+ ex.getMessage());
        return false;
    }
    }
    
    public String readPublicTokenInfo(String token) throws Exception{
        byte[] bytes = Base64.getDecoder().decode(token);
        String rawTokenDecoded = new String(bytes);

        //System.out.println(rawTokenDecoded);

        String[] tokenParts = rawTokenDecoded.split(":");
        Long timestamp = Long.parseLong(tokenParts[0]);
        System.out.println(new Date(timestamp));
        System.out.println(tokenParts[2]+"<--- ");
    return tokenParts[2];    
    }
}
