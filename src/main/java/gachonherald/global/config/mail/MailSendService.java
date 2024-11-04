package gachonherald.global.config.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MailSendService {
    @Autowired
    private JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String DEFAULT_IMAGE = "default.png";
    public static final long EXPIRATION_TIME = 3 * 60 * 1000L;
    public static final String VERIFY_EMAIL_KEYWORD = "VERIFY_EMAIL";

    public String makeRandomNumber(String email) {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }
        String authNumber = randomNumber;
        redisTemplate.opsForValue().set(
                VERIFY_EMAIL_KEYWORD+email,
                authNumber,
                EXPIRATION_TIME,
                TimeUnit.MILLISECONDS
        );
        return authNumber;
    }

    public void joinEmail(String email) {
        String authNumber = makeRandomNumber(email);
        String setFrom = "gachonherald@gmail.com";
        String toMail = email;
        String title = "This is the verification email from The Gachon Herald.";
        String content =
                "Thank you for visiting The Gachon Herald." +
                        "<br><br>" +
                        "Your verification code is " + authNumber +
                        "<br>" +
                        "Please enter the verification code correctly.";
        mailSend(setFrom, toMail, title, content);
    }

    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
