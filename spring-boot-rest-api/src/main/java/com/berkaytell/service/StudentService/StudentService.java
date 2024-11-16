package com.berkaytell.service.StudentService;

import com.berkaytell.dto.GetAllStudentsDto;
import com.berkaytell.dto.GetSingleStudentDto;

import java.util.List;

public interface StudentService {
    GetSingleStudentDto findById(Long id);

    List<GetAllStudentsDto> getAll();

}
