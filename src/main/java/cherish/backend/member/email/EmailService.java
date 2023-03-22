package cherish.backend.member.email;

import java.util.List;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;

}
