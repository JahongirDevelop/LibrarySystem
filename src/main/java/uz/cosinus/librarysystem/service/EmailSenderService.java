package uz.cosinus.librarysystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.cosinus.librarysystem.exception.SendVerificationCodeException;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    public static final Random random = new Random();

    public String sendVerificationCode(String email) {

        try {
            // Validate email format
            if (!isValidEmail(email)) {
                throw new SendVerificationCodeException("Invalid email address: " + email);
            }

            // Generate verification code
            String verificationCode = String.valueOf(random.nextInt(90000) + 10000);
            String message = "This is your verification code: " + verificationCode;

            // Create and send the email
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(message);
            mailSender.send(simpleMailMessage);

            return verificationCode;
        } catch (Exception e) {
            throw new SendVerificationCodeException("Error sending the verification code. " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        // Use a regular expression for basic email format validation
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

}