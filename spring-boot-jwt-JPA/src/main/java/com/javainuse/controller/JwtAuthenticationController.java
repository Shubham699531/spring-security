package com.javainuse.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.javainuse.service.JwtUserDetailsService;


import com.javainuse.config.JwtTokenUtil;
import com.javainuse.model.JwtRequest;
import com.javainuse.model.JwtResponse;
import com.javainuse.model.Librarian;
import com.javainuse.model.Student;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	//This method is called by JwtRequestFilter, if there is no token in the 
	//incoming request
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		//calls authenticate method, after extracting username and password from
		//incoming Jwt request
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		//user details are fetched from the database
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		//this is the step where token is generated
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/registerStu", method = RequestMethod.POST)
	public ResponseEntity<?> saveStudentUser(@RequestBody Student user) throws Exception {
		return ResponseEntity.ok(userDetailsService.saveStu(user));
	}
	
	@RequestMapping(value = "/registerLib", method = RequestMethod.POST)
	public ResponseEntity<?> saveLibrarianUser(@RequestBody Librarian user) throws Exception {
		return ResponseEntity.ok(userDetailsService.saveLib(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			//calls authenticate method of Spring Security's Authentication Manager
			//by wrapping userName and password in a new 
			//UsernamePasswordAuthenticationToken object
			//and checks for invalid credentials
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}