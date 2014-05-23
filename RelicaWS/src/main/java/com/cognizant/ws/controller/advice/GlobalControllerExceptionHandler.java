package com.cognizant.ws.controller.advice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.cognizant.ws.exception.AssetNotFoundException;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.exception.DocumentumException;
import com.cognizant.ws.exception.Error;
import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.exception.PolicyNotFoundException;
import com.cognizant.ws.exception.RequestMD5Exception;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends
		ResponseEntityExceptionHandler {

	private static final Logger LOG = Logger
			.getLogger(GlobalControllerExceptionHandler.class);

	

	@ExceptionHandler( { AssetNotFoundException.class })
	public ResponseEntity<MessageResponse> handleAssetNotFoundException(
			AssetNotFoundException pe) {
		LOG.info(pe.getMessage()
				+ "...............handleAssetNotFoundException............"
				+ pe.getLocalizedMessage());
		MessageResponse em = new MessageResponse();
		// em.setTitle("Asset not found");
		em.setCode("400");
		em.setMessage(pe.getMessage());
		em.setDescription(pe.getMessage());
		/*
		 * ErrorType et = new ErrorType(); et.setDesc(pe.getMessage());
		 * et.setMsg("Asset not found"); em.setErrorType(et);
		 */
		return new ResponseEntity<MessageResponse>(em, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler( { PolicyNotFoundException.class })
	public ResponseEntity<MessageResponse> handleAssetNotFoundException(
			PolicyNotFoundException pe) {
		LOG.info(pe.getMessage()
				+ "...............handlePolicyNotFoundException............"
				+ pe.getLocalizedMessage());
		MessageResponse em = new MessageResponse();
		em.setCode("404");
		em.setMessage(pe.getMessage());
		em.setDescription(pe.getLocalizedMessage());
		return new ResponseEntity<MessageResponse>(em, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler( { ReplicaWSException.class })
	public ResponseEntity<Object> handleReplicaWSException(
			ReplicaWSException pe) {
		LOG.info(pe.getMessage()
				+ "...............handleReplicaWSException............"
				+ pe.response);
		LinkedHashMap<String, String> response=pe.response;
		Object obj=null;
		if(response != null)
		{
			obj=pe.response;
		}
		else
		{
			MessageResponse em = new MessageResponse();
			em.setCode("404");
			em.setMessage(pe.getMessage());
			em.setDescription(pe.getLocalizedMessage());	
			obj=em;
		}
		return new ResponseEntity<Object>(obj, HttpStatus.OK);
	}

	
	  @ExceptionHandler(Exception.class) 
	  public ResponseEntity<MessageResponse>   handleException(Exception ex) { 
		  MessageResponse em = new MessageResponse(); 
	 
		  em.setCode("403"); 
		  em.setDescription(ex.getLocalizedMessage()); 
		  em.setMessage(ex.getMessage());
		  return new ResponseEntity<MessageResponse>(em, HttpStatus.NOT_FOUND);
	  }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ObjectError> globalErrors = ex.getBindingResult()
				.getGlobalErrors();
		Error errors = new Error();
		String error;

		List<MessageResponse> emm = new ArrayList<MessageResponse>();

		LOG.info(ex.getMessage()
				+ "...............handleMethodArgumentNotValid............");
		for (FieldError fieldError : fieldErrors) {
			error = fieldError.getField() + ", "
					+ fieldError.getDefaultMessage();
			MessageResponse em = new MessageResponse();
			LOG
					.info(error
							+ "...............handleMethodArgumentNotValid...72.........");
			em.setCode("400");
			em.setMessage("validation failed");
			em.setDescription(fieldError.getDefaultMessage());
			emm.add(em);
			errors.setValidationErrors(emm);
		}
		for (ObjectError objectError : globalErrors) {
			error = objectError.getObjectName() + ", "
					+ objectError.getDefaultMessage();
			MessageResponse em = new MessageResponse();
			LOG
					.info(error
							+ "...............handleMethodArgumentNotValid...77.........");
			em.setCode("400");
			em.setMessage("validation failed");
			em.setDescription(objectError.getDefaultMessage());
			emm.add(em);
			errors.setValidationErrors(emm);
		}
		// ErrorMessage errorMessage = new ErrorMessage(errors);
		LOG.info(errors
				+ "...............handleMethodArgumentNotValid......81......");
		return new ResponseEntity(errors, headers, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Throwable mostSpecificCause = ex.getMostSpecificCause();
		MessageResponse em = new MessageResponse(); 
		em.setCode(status.value()+"");
		em.setMessage(mostSpecificCause.getMessage());
		em.setDescription(ex.getMessage());
		return new ResponseEntity(em, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String mostSpecificCause = ex.getLocalizedMessage();
		MessageResponse em = new MessageResponse(); 
		em.setCode(status.value()+"");
		em.setMessage(ex.getMessage());
		em.setDescription(mostSpecificCause);
		return new ResponseEntity(em, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleNoSuchRequestHandlingMethod(
			NoSuchRequestHandlingMethodException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String mostSpecificCause = ex.getLocalizedMessage();
		MessageResponse em = new MessageResponse(); 
		em.setCode(status.value()+"");
		em.setMessage(ex.getMessage());
		em.setDescription(mostSpecificCause);
		return new ResponseEntity(em, headers, status);
	}
	
	@ExceptionHandler({ RequestMD5Exception.class })
	public ResponseEntity<MessageResponse> handleRequestMD5Exception(
			RequestMD5Exception pe) {
		LOG.info(pe.getMessage()
				+ "...............handleRequestMD5Exception............"
				+ pe.getLocalizedMessage());
		MessageResponse em = new MessageResponse();
		em.setCode("404");
		em.setMessage(pe.getMessage());
		em.setDescription(pe.getLocalizedMessage());
		em.setJobId("0");
		em.setPath("");
		em.setError(pe.getMessage());
		return new ResponseEntity<MessageResponse>(em, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler({ DocumentumException.class })
	public ResponseEntity<MessageResponse> handleRequestProxyException(
			DocumentumException de) {
		LOG.info(de.getMessage()
				+ "...............handleRequestProxyException............"
				+ de.getLocalizedMessage());
		MessageResponse em = new MessageResponse();
		em.setCode("404");
		em.setMessage(de.getMessage());
		em.setDescription(de.getDescription());		
		return new ResponseEntity<MessageResponse>(em, HttpStatus.NOT_FOUND);
	}
	

}
