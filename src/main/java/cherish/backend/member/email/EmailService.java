package cherish.backend.member.email;

public interface EmailService {
    String sendMessage(String to, String content);

}
