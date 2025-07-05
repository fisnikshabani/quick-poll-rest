package com.appress.quick_poll.handler;

import com.appress.quick_poll.dto.error.ErrorDetails;
import com.appress.quick_poll.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request ) {
        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setTimestamp(new Date().getTime());
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetails.setTitle("Resource not found");
        errorDetails.setDetail(rnfe.getMessage());
        errorDetails.setDeveloperMessage(rnfe.getClass().getName());

        return new ResponseEntity<>(errorDetails, null, HttpStatus.NOT_FOUND);
    }

}
