package com.csk.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.csk.ppmtool.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user=(User)target;
		if(user.getPassword().length()<6) {
			errors.rejectValue("password", "Lenght","Password must be at least 6 character");
		}
		if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match", "Passwords must match");

        }
		//confirmPassword
	}

}
