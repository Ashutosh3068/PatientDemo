package com.patientDemo.exception;

import com.patientDemo.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
        public ResponseEntity<ErrorDetails> notFoundResource(
                ResourceNotFound exceptionMessage,
                WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exceptionMessage.getMessage(),webRequest.getDescription(false));
       return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    }

