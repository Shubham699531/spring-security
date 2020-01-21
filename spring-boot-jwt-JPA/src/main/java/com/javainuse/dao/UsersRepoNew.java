package com.javainuse.dao;

import java.util.List;

import com.javainuse.model.Librarian;
import com.javainuse.model.Student;

public interface UsersRepoNew {
	
	/**
	 * 
	 * @param student
	 * @return the registered student details
	 */
	Student registerStudent(Student student);
	
	/**
	 * 
	 * @param librarian
	 * @return the registered librarian details
	 */
	Librarian registerLibrarian(Librarian librarian);
	
}