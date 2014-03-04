package com.util;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.IllegalFieldValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dto.MapComputer;
import com.util.CustomDateValidator;


public class CustomDateValidator implements ConstraintValidator<ValidDateTime, String>{

	final Logger LOG = LoggerFactory.getLogger(CustomDateValidator.class);
	
	@Override
	public void initialize(ValidDateTime arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {		
		LOG.debug("In validation : "+date);
		if(date == "") return true;
		try {
			MapComputer.getLocalDateFormat().parseDateTime(date);
		}
		catch (IllegalFieldValueException e) {
            LOG.error(e.getMessage());
            return false;
        }catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return false;
        }
		LOG.debug("return true");
		return true;
	}
	

}
