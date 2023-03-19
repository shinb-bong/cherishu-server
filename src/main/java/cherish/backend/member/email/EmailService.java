package cherish.backend.member.email;

import org.springframework.stereotype.Service;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
}
