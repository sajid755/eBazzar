package com.shoppingcart.eBazzar.DAO;

import com.shoppingcart.eBazzar.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAO implements IStudentDAO{

    private EntityManager entityManager;

    @Autowired
    public StudentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findById(Integer id) {

        return entityManager.find(Student.class,id);
    }

    @Override
    public List<Student> getAll() {
        TypedQuery<Student> tq = entityManager.createQuery("From Student order by id desc",Student.class);
        return tq.getResultList();
    }
}
