package cherish.backend.member.controller;

import cherish.backend.member.email.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailApiController {
    private final EmailService emailService;

    public EmailApiController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/user/emailConfirm")
    public String emailConfirm(@RequestParam String email) throws Exception {
        String confirm = emailService.sendSimpleMessage(email);
        return confirm;
    }
}
