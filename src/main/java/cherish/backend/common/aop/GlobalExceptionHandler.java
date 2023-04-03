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

    private static final String DEFAULT_ERROR_MSG = "알 수 없는 에러가 발생했습니다. 운영자에게 문의 바랍니다.";
    private static final ErrorResponseDto DEFAULT_ERROR_RESPONSE = new ErrorResponseDto(DEFAULT_ERROR_MSG);

    private ErrorResponseDto createError(Exception e) {
        log.error(e.getMessage(), e);
        return DEFAULT_ERROR_RESPONSE;
    }

    private ErrorResponseDto createError(Exception e, String message) {
        log.error(e.getMessage(), e);
        return new ErrorResponseDto(message);
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

        return createError(e, builder.toString());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorResponseDto handleTypeException(Exception e){
        return createError(e, "타입 오류입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponseDto handleIllegalStateException(IllegalStateException e){
        return createError(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponseDto handleCredential(BadCredentialsException e){
        return createError(e, "로그인에 실패하였습니다.");
    }


    // 공통 예외 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleException(Exception e) {
        return createError(e);
    }
}
