package cherish.backend.member.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Profile("google")
@Service
public class GoogleEmailService implements EmailService {

    private final JavaMailSenderImpl mailSender;

    @Override
    public String sendMessage(String to, String content) {
        String from = "noreply-cherishu@gmail.com";
        String title = "CherishU 회원 가입 인증 이메일 입니다.";
        sendMail(from, to, title, content);
        return content;
    }
    //이메일 전송 메소드
    public void sendMail(String from, String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }
}
