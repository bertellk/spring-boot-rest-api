package com.berkaytell.service.student;

import com.berkaytell.dto.student.GetAllStudentsDto;
import com.berkaytell.dto.student.GetSingleStudentDto;

import java.util.List;

public interface StudentService {
    GetSingleStudentDto findById(Long id);

    List<GetAllStudentsDto> getAll();

}
