package com.media.exception;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.media.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	// handel resource Not fount exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handelResourceNotFoundException( ResourceNotFoundException resourceNotFoundException ,
			WebRequest webRequest
			){
		
		ErrorDetails errorDetails = new ErrorDetails( new Date() , resourceNotFoundException.getMessage() ,
				webRequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>( errorDetails , HttpStatus.NOT_FOUND );
	}
	
	// handle sMedia exception
	@ExceptionHandler(SMediaException.class)
	public ResponseEntity<ErrorDetails> handelSMediaException(SMediaException sMediaException , WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(new Date() , sMediaException.getMessage() , webRequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>( errorDetails , HttpStatus.BAD_REQUEST );
	}
	
	// handel global exception
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> handelGlobalException(RuntimeException exception , WebRequest webRequest ){
		ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage() , webRequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>( errorDetails , HttpStatus.INTERNAL_SERVER_ERROR );
	}

	
	// handel MethodArgumentNotValidException
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		Map<String, String> output = new HashMap<>();
		
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		
		allErrors.forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			
			output.put(fieldName, message);
			
		}
			);
		
		return new ResponseEntity<Object>( output , HttpStatus.BAD_REQUEST );
	}
	
	// access Denied exception
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handelAccessDeniedException(AccessDeniedException exception , WebRequest webRequest ){
		ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage() , webRequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>( errorDetails , HttpStatus.INTERNAL_SERVER_ERROR );
	}
	
}



















