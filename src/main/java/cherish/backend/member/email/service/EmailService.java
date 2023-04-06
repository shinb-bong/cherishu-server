package cherish.backend.member.email.service;

public interface EmailService {
    String sendMessage(String to, String content);

}
