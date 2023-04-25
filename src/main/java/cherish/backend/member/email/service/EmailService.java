package cherish.backend.member.email.service;

public interface EmailService {
    void sendMessage(String to, String code);
}
