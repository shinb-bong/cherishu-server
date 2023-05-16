package cherish.backend.member.email.service;

import cherish.backend.common.constant.CommonConstants;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async(CommonConstants.MAIL_TASK_EXECUTOR_NAME)
    void sendMessage(String to, String code);
}
