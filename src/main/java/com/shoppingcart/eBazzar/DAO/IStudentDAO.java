package com.shoppingcart.eBazzar.DAO;

import com.shoppingcart.eBazzar.model.Student;

import java.util.List;

public interface IStudentDAO {
    void save(Student student);
    Student findById(Integer id);
    List<Student> getAll();
}
