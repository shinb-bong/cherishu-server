package cherish.backend.member.dto;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmailSenderDto {
    public static final String FROM_EMAIL = "cherishu.fullstack@gmail.com"; // 보내는 사람
    public static final String subject = "Cherish 인증코드 발송"; // 제목
    private final String to; // 받는 사람
    private final String content; // 본문

    @Builder
    public EmailSenderDto(final String to, final String content) {
        this.to = to;
        this.content = content;
    }

    public SendEmailRequest toSendRequestDto() {
        final Destination destination = new Destination()
                .withToAddresses(this.to);

        final Message message = new Message()
                .withSubject(createContent(this.subject))
                .withBody(new Body()
                        .withHtml(createContent(this.content)));

        return new SendEmailRequest()
                .withSource(FROM_EMAIL)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(final String text) {
        return new Content()
                .withCharset("UTF-8")
                .withData(text);
    }
}
