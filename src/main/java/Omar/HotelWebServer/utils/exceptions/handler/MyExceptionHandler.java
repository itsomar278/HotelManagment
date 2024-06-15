package Omar.HotelWebServer.utils.exceptions.handler;


import Omar.HotelWebServer.utils.exceptions.EmptyResultException;
import Omar.HotelWebServer.utils.exceptions.ResourceExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyResultException.class)
    public ResponseEntity<Object> handleEmptyResult(EmptyResultException ex , WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDateTime(LocalDateTime.now());
        exceptionResponse.setMessage(ex.getMessage());
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<Object> handleAlreadyExists(ResourceExistsException ex , WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDateTime(LocalDateTime.now());
        exceptionResponse.setMessage(ex.getMessage());
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
        return responseEntity;
    }


}