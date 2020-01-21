package com.javainuse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.javainuse.model.Librarian;
import com.javainuse.model.Login;
import com.javainuse.model.Student;

@Repository
@Transactional
public class UsersRepoImpl implements UsersRepoNew{
	
	@Autowired
	private EntityManager mgr;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public Student registerStudent(Student student){
		Login login = new Login();
		//Encrypting username and password before saving in database
//		login.setUserName(AES.encrypt(student.getUserName(), key));
//		login.setPassword(AES.encrypt(student.getPassword(), key));
		login.setUsername(student.getUsername());
		login.setPassword(bcryptEncoder.encode(student.getPassword()));
		login.setRole("STUDENT");
		mgr.persist(login);
		mgr.persist(student);

		return student;
	}

	@Override
	public Librarian registerLibrarian(Librarian librarian) {
		Login login = new Login();
		//Encrypting username and password before saving in database
//		login.setUserName(AES.encrypt(student.getUserName(), key));
//		login.setPassword(AES.encrypt(student.getPassword(), key));
		login.setUsername(librarian.getUsername());
		login.setPassword(bcryptEncoder.encode(librarian.getPassword()));
		login.setRole("LIBRARIAN");
		mgr.persist(login);
		mgr.persist(librarian);
		return librarian;
	}

}
