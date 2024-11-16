package com.berkaytell.service.teacher;

import com.berkaytell.dto.teacher.GetAllTeachersDto;
import com.berkaytell.dto.teacher.GetSingleTeacherDto;

import java.util.List;

public interface TeacherService {
    GetSingleTeacherDto findById(Long id);

    List<GetAllTeachersDto> getAll();
}
