package com.csk.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csk.ppmtool.domain.User;
import com.csk.ppmtool.payload.JWTLoginSuccessResponse;
import com.csk.ppmtool.payload.LoginRequest;
import com.csk.ppmtool.security.JwtTokenProvider;
import com.csk.ppmtool.service.MapValidationErrorsService;
import com.csk.ppmtool.service.UserService;
import com.csk.ppmtool.validator.UserValidator;

import static com.csk.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

	@Autowired
	private MapValidationErrorsService mapValidationErrorsService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorsService.MapValidationErrorsService(result);
		if (errorMap != null)
			return errorMap;

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=TOKEN_PREFIX+tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
		// Valid password match
		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = mapValidationErrorsService.MapValidationErrorsService(result);
		if (errorMap != null)
			return errorMap;
		User newUser = userService.saveUser(user);
		System.out.println(newUser.getId());
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
}
