package com.berkaytell.repository;

import com.berkaytell.model.Teacher;   //Model import edilmeli
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
//Burada verilen long teachNum için

}
