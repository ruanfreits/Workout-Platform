package com.gym_project.projeto_bulkhouse.Services;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class MailService {
    @Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String remetente;
	
	public String enviarEmailTexto(String destinatario, String assunto, String mensagem) throws Exception {
		
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(remetente);
			simpleMailMessage.setTo(destinatario);
			simpleMailMessage.setSubject(assunto);
			simpleMailMessage.setText(mensagem);
			javaMailSender.send(simpleMailMessage);
			return "Email sended Successfully";
		}catch(Exception e) {
			return e.getLocalizedMessage();
		}
	}
}
