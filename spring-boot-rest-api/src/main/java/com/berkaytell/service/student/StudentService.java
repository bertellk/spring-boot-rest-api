package com.berkaytell.service.student;

import com.berkaytell.dto.student.GetAllStudentsDto;
import com.berkaytell.dto.student.GetSingleStudentDto;
import com.berkaytell.dto.student.InsertStudentDto;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;
import jakarta.mail.MessagingException;
import jakarta.persistence.Tuple;

import java.util.List;

public interface StudentService {
    GetSingleStudentDto findById(Long id);

    DataResult<GetSingleStudentDto> findByIdDataResult(Long id);

    List<GetAllStudentsDto> getAll();

    Result insert(InsertStudentDto dto) throws MessagingException;

    String updateHasPayTheFee(Long studentId, Boolean hasPayTheFee);

    String delete(Long id);

    Tuple getTuple(String name);

    Result insertResult(InsertStudentDto dto);

    void detachStudentsFromCourse(Long courseId);

    boolean existsById(Long id);

    Integer getAgeById(Long id);

    boolean getHasPayTheFeeById(Long id);

    Result assignStudent(Long studentId, Long courseId);
}
