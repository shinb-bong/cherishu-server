package cherish.backend.member.email;

import cherish.backend.member.dto.EmailSenderDto;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendEmailService implements EmailService{
    private final AmazonSimpleEmailService amazonSimpleEmailService;
    @Override
    public String sendSimpleMessage(String to) throws Exception {
        String code = EmailCode.createCode().getCode();
        final EmailSenderDto senderDto = EmailSenderDto.builder() // 1
                .to(to)
                .content(code)
                .build();

        final SendEmailResult sendEmailResult = amazonSimpleEmailService // 2
                .sendEmail(senderDto.toSendRequestDto());

        sendingResultMustSuccess(sendEmailResult); // 3
        return code;
    }

    private void sendingResultMustSuccess(SendEmailResult sendEmailResult) {
        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            log.error("{}", sendEmailResult.getSdkResponseMetadata().toString());
        }
    }
}
