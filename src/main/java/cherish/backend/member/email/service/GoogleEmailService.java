package cherish.backend.member.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Profile("google")
public class GoogleEmailService implements EmailService {

    private final JavaMailSenderImpl mailSender;

    @Override
    public void sendMessage(String to, String code) {
        String from = "noreply-cherishu@gmail.com";
        String title = "[체리슈] 인증번호가 발급되었습니다.";
        sendMail(from, to, title, code);
    }

    //이메일 전송 메소드
    public void sendMail(String from, String to, String title, String code) {
        MimeMessage message = mailSender.createMimeMessage();
        // true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            // true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
            String template = """
                <p>
                  안녕하세요. 체리슈입니다.<br><br>
                  소중한 사람을 위한 선물 큐레이션 서비스, 체리슈를 이용해 주셔서 감사합니다.<br><br>
                  아래 인증번호를 입력하여 이메일 인증을 완료해 주세요. 개인정보 보호를 위해 인증번호는 최대 5분 간만 유효합니다.<br><br>
                </p>
                <br>
                <p>
                  <strong>인증번호 : %s</strong>
                </p>
                """;
            helper.setText(String.format(template, code), true);
            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            log.error(e.getMessage(), e);
            throw new MailSendException("메일 전송에 실패했습니다.");
        }

        log.info("A mail has been sent to {}\ncode : {}", to, code);
    }
}
