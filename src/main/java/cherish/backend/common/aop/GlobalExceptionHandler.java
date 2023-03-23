package cherish.backend.common.aop;

import cherish.backend.common.dto.ErrorResponseDto;
import cherish.backend.common.exception.ApiRequestNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private ErrorResponseDto createError(Exception e) {
        log.error(e.getMessage(), e);
        if (ObjectUtils.isEmpty(e.getMessage())) {
            return new ErrorResponseDto(NO_ERROR_MESSAGE);
        }
        return new ErrorResponseDto(e.getMessage());
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

        return createError(new ApiRequestNotValidException(builder.toString()));
    }

    // 공통 예외 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleException(Exception e) {
        return createError(e);
    }
}
