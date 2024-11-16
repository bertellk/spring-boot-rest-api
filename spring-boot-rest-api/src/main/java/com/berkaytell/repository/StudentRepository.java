package com.berkaytell.repository;

import com.berkaytell.model.Student;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // query yazmadan veri çekmek
    Student findByName(String name);
    Long countByCity(String city);
    List<Student> findAllByIsActiveTrue();
    List<Student> findByAgeIsNotNull();

    //JPQL
    // direkt entity dönecek
    @Query("""
            SELECT s FROM Student s WHERE s.name = :studentName
            """)
    Student findByNameJPQL(@Param("studentName") String name);

    //native query
    // direkt entity dönecek
    @Query(value = """
            SELECT * FROM students WHERE name = :studentName
            """, nativeQuery = true)
    Tuple findByCustomName(@Param("studentName") String name);

//    @Modifying
//    @Query("insert into (1, 'ahmet') blabla")
    // modifying kullandığın sorguyu çağıran metota @Transactional
}
