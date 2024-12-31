package com.shoppingcart.eBazzar;

import com.shoppingcart.eBazzar.DAO.IStudentDAO;
import com.shoppingcart.eBazzar.model.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class EBazzarApplication {

	public static void main(String[] args) {

		SpringApplication.run(EBazzarApplication.class, args);

	}

	@Bean
	public CommandLineRunner clr(IStudentDAO studentDAO){
		return runner ->{
			createStudent(studentDAO);
		};
	}

	private void createStudent(IStudentDAO studentDAO) {
		Student dummyStudent = new Student(
				"John",                         // firstName
				"Doe",                          // lastName
				"johndoe@example.com",          // email
				LocalDate.of(2000, 1, 15),      // dateOfBirth
				LocalDateTime.now()             // enrollmentDate
		);
		studentDAO.save(dummyStudent);

		Student test = studentDAO.findById(dummyStudent.getId());

		List<Student> allList = studentDAO.getAll();

		for(var x : allList){
			System.out.println(x.toString());
		}

	}

}
