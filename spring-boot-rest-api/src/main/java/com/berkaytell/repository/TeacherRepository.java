package com.berkaytell.repository;

import com.berkaytell.model.Teacher;   //Model import edilmeli
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
//Burada verilen long teach Id için

    //query yazmadan veri çekme JPA idi sanırım bu

    Teacher findByName(String name);
    List<Teacher> findAllByIsActiveTrue();
    List<Teacher> findAllByCityIsNotNull();

    //JPQL
    //Entity döndürür
    @Query("""
           SELECT t FROM Teacher t WHERE t.name = :teacherName
            """)
    //Bu kısmı yazmadan sadece Query yazınca kızıyo
    Teacher findByNameJQPL(@Param("teacherName")String name);

    //native query imiş
    @Query(value = """
            SELECT * FROM teacher WHERE name = :teacherName
            """, nativeQuery = true)
    Tuple findByCustomName(@Param("teacherName")String name);

    //@modifying
    //@Query("insert into (1,'Berkay','Tellikavak') falan filan
    //Modifying varsa @Transactional kullanılmalı sorguyu çağıran metod için
}
