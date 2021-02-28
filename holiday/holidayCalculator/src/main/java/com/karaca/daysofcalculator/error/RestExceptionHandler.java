package com.karaca.daysofcalculator.error;

import com.karaca.daysofcalculator.Dto.GenericResponse;
import com.karaca.daysofcalculator.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
        private MessageSource messageSource;

    @ExceptionHandler(NotEnoughDayOffException.class)
    public ResponseEntity<Object> handleNotEnoughDayOffException(final RuntimeException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message= "message.not.enough.day.off";
        return GenerateResponseByStatusAndMessage(exception, status,message ,request);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(final RuntimeException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message= "message.username.not.found";
        return GenerateResponseByStatusAndMessage(exception, status,message ,request);
    }

    @ExceptionHandler(InvalidDayOffBoundException.class)
    public ResponseEntity<Object> handleInvalidDayOffBoundException(final RuntimeException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message= "message.invalid.day.off.bound";
        return GenerateResponseByStatusAndMessage(exception, status,message ,request);
    }

    @ExceptionHandler(InvalidDayOffException.class)
    public ResponseEntity<Object> handleInvalidDayOffException(final RuntimeException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message= "message.invalid.day.off";
        return GenerateResponseByStatusAndMessage(exception, status,message ,request);
    }

    @ExceptionHandler(InvalidRequestedDayOff.class)
    public ResponseEntity<Object> handleInvalidRequestedDayOff(final RuntimeException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message= "message.invalid.request.day.off";
        return GenerateResponseByStatusAndMessage(exception, status,message ,request);
    }

    @ExceptionHandler(DayOffApprovePendingException.class)
    public ResponseEntity<Object> handleDayOffApprovePendingException(final RuntimeException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message= "message.already.approved";
        return GenerateResponseByStatusAndMessage(exception, status,message ,request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(final RuntimeException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String message= "message.user.already.exists";
        return GenerateResponseByStatusAndMessage(exception, status,message ,request);
    }

    private ResponseEntity<Object> GenerateResponseByStatusAndMessage(final RuntimeException exception,
                                                                      HttpStatus status,
                                                                      String message,
                                                                      final WebRequest request) {
        final GenericResponse response = new GenericResponse(status, messageSource.getMessage(message, null, request.getLocale()));
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

}
