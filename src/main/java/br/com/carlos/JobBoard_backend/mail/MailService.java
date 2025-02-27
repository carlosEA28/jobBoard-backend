package br.com.carlos.JobBoard_backend.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String sendEmailTest(String to, String topic, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(remetente);
            mailMessage.setTo(to);
            mailMessage.setSubject(topic);
            mailMessage.setText(message);
            javaMailSender.send(mailMessage);
            return "Email enviado";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
