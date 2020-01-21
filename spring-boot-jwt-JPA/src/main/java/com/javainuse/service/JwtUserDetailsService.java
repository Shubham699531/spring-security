package com.javainuse.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javainuse.dao.UserDao;
import com.javainuse.dao.UsersRepoNew;
import com.javainuse.model.Librarian;
import com.javainuse.model.Login;
import com.javainuse.model.Student;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsersRepoNew userDao;
	
	@Autowired
	private UserDao userdao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	//Spring Security Core USER implements UserDetails interface
	//and thus here we are passing parent reference
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login user = userdao.findByUsername(username);
		System.out.println("4th");
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		//This is not our database user, this is spring security core USER.
		//We are returning this spring security core USER by using the information that 
		//we fetch from our database by using our 
		//own named query which searches by Username
		
		List<String> roles = new ArrayList<>();
		roles.add(user.getRole());
		Set<GrantedAuthority>authorities = new HashSet<>(roles.size());

		for (String role : roles)
		    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}
	
	public Student saveStu(Student user) {
//		Student newUser = new Student();
		
		return userDao.registerStudent(user);
	}
	
	public Librarian saveLib(Librarian user) {
//		DAOUser newUser = new DAOUser();
//		user.setUsername(user.getUsername());
//		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.registerLibrarian(user);
	}
}