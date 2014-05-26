package com.cognizant.ws.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DateValidator implements  org.springframework.validation.Validator {

	private static final Logger LOG = Logger.getLogger(DateValidator.class);
	
	public boolean supports(Class<?> c) {
		return true;
	}

	public void validate(Object bean, Errors err) {
		Class<? extends Object> beanClass = bean.getClass();
		Field[] fields = beanClass.getDeclaredFields();
		
		for (Field field : fields) {
			
			DateFormatValid annotation =field.getAnnotation(DateFormatValid.class);
		
			if(annotation != null){
				
				String format = annotation.formate();
				try {
					
					field.setAccessible(true);
					LOG.info(field.get(bean)+":DateValidator format:"+format);
					if(!isThisDateValid((Date)field.get(bean),format)){
						err.rejectValue(field.getName(),beanClass.getName()+field.getName(),"You have provided invalid date formate. Correct format is " + format);
					}
					
				} catch (Exception e) {
					LOG.error("Exception While Validating the dateFormat::"+e.getMessage());
				}
			}
		}
	}
	
	public boolean isThisDateValid(Date dateToValidate, String dateFromat) throws ParseException{
		 
		if(dateToValidate == null){
			return false;
		}
 
		
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
				sdf.setLenient(false);
		 
					String dt= sdf.format(dateToValidate);
					sdf.parse(dt);
					LOG.info("isThisDateValid:");
					return true;
			} catch (Exception e) {
				// TODO: handle exception
				LOG.error("Exception while validating Date::"+e.getMessage());
				return false;
			}
 
		
	}
	


	public ExecutableValidator forExecutables() {
		// TODO Auto-generated method stub
		return null;
	}

	public BeanDescriptor getConstraintsForClass(Class<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T unwrap(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Set<ConstraintViolation<T>> validate(T arg0, Class<?>... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Set<ConstraintViolation<T>> validateProperty(T arg0,
			String arg1, Class<?>... arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> arg0,
			String arg1, Object arg2, Class<?>... arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	
}