package com.csk.ppmtool.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorsService {
	
	public ResponseEntity<?> MapValidationErrorsService(BindingResult result){
		if(result.hasErrors()) {
			Map<String,String> errMap=new HashMap<>();
			for(FieldError error:result.getFieldErrors()) {
				errMap.put(error.getField(),error.getDefaultMessage());
			}
			return new ResponseEntity<Map<String,String>>(errMap,HttpStatus.BAD_REQUEST);
		}
		return null;
	}
}
