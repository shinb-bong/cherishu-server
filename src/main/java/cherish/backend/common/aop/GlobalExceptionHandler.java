package cherish.backend.common.aop;

import cherish.backend.common.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String NO_ERROR_MESSAGE = "No error message from server.";
    private static final ErrorResponseDto DEFAULT_ERROR_RESPONSE = new ErrorResponseDto(NO_ERROR_MESSAGE);

    private ErrorResponseDto createError(Exception e) {
        log.error(e.getMessage(), e);
        if (ObjectUtils.isEmpty(e.getMessage())) {
            return DEFAULT_ERROR_RESPONSE;
        }
        return new ErrorResponseDto(e.getMessage());
    }

    private ErrorResponseDto createError(String s) {
        if (ObjectUtils.isEmpty(s)) {
            return DEFAULT_ERROR_RESPONSE;
        }
        log.error(s);
        return new ErrorResponseDto(s);
    }

    // 자바 빈 검증 예외 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        return createError(builder.toString());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorResponseDto handleTypeException(Exception e){
        return createError("타입 오류입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponseDto handleIllegalStateException(IllegalStateException e){
        return createError(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponseDto handleCredential(BadCredentialsException e){
        return createError("로그인에 실패하였습니다.");
    }


    // 공통 예외 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleException(Exception e) {
        return createError(e);
    }
}
