package com.berkaytell.service.StudentService;

import com.berkaytell.dto.GetAllTeachersDto;
import com.berkaytell.dto.GetSingleTeacherDto;

import java.util.List;

public interface TeacherService {
    GetSingleTeacherDto findById(Long teachNum);

    List<GetAllTeachersDto> getAll();
}
