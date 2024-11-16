package com.berkaytell.service.student;

import com.berkaytell.dto.student.GetAllStudentsDto;
import com.berkaytell.dto.student.GetSingleStudentDto;
import com.berkaytell.dto.student.InsertStudentDto;
import jakarta.persistence.Tuple;

import java.util.List;

public interface StudentService {
    GetSingleStudentDto findById(Long id);

    List<GetAllStudentsDto> getAll();

    String insert(InsertStudentDto dto);

    String updateHasPayTheFee(Long studentId, Boolean hasPayTheFee);

    String delete(Long id);

    Tuple getTuple(String name);
}
