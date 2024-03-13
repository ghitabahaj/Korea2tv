package  com.youcode.korea2tv.exception;

import  com.youcode.korea2tv.exception.custom.NotFoundMediaException;
import  com.youcode.korea2tv.exception.custom.NotFoundUserException;
import com.example.vistreamv2.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
//    Handle this classes to customize my exception created
    @ExceptionHandler({IllegalArgumentException.class, NotFoundMediaException.class})
    public ResponseEntity<Response<String>> handelBadRequestException(Exception ex){
        String getErrorMessage = ex.getMessage();
        return ResponseEntity.badRequest().body(Response.<String>builder()
                .error(getErrorMessage)
                .build());
    }
    //Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handelMethodArgumentNotValidException(MethodArgumentNotValidException ev){
        Map<String, String> setFieldsValidation = new HashMap<>();
        ev.getBindingResult().getFieldErrors().forEach(
                e -> setFieldsValidation.put(e.getField(), e.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(Response.<Map<String, String>>builder()
                .errorsValidation(setFieldsValidation)
                .build());
    }

    //Authentication Forbidden & Unauthorized
//    @ExceptionHandler(BadCredentialsException.class)
//    public ProblemDetail badCredentialsException(Exception ex){
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
//        problemDetail.setProperty("bad_credentials", "Authentication Failure");
//        return problemDetail;
//    }
//    @ExceptionHandler(AccessDeniedException.class)
//    public ProblemDetail accessDeniedException(Exception ex){
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//        problemDetail.setProperty("access_denied", "Unauthorized Failure");
//        return problemDetail;
//    }

    @ExceptionHandler(AccessDeniedException.class)
    ProblemDetail handleAccessDeniedException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
        problemDetail.setProperty("access_denied", "Unauthorized Failure");
        return problemDetail;
    }
    @ExceptionHandler({NotFoundUserException.class, BadCredentialsException.class})
    ProblemDetail handleAuthenticationException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
        problemDetail.setProperty("bad_credentials", "Username or password is incorrect.");
        return problemDetail;
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    ProblemDetail handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
        problemDetail.setProperty("credentials", "Login credentials are missing.");
        return problemDetail;
    }

//    @ExceptionHandler(Exception.class)
//    ProblemDetail handleOtherException(Exception ex) {
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), ex.getMessage());
//        problemDetail.setProperty("server", "A server internal error occurs.");
//        return problemDetail;
//    }

    @ExceptionHandler(NoHandlerFoundException.class)
    ProblemDetail handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
        problemDetail.setProperty("API", "This API endpoint is not found.");
        return problemDetail;
    }


}
