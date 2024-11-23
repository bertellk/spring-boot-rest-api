package com.berkaytell.service.teacher;

import com.berkaytell.dto.teacher.GetAllTeachersDto;
import com.berkaytell.dto.teacher.GetSingleTeacherDto;
import com.berkaytell.dto.teacher.InsertTeacherDto;
import jakarta.persistence.Tuple;

import java.util.List;

public interface TeacherService {
    GetSingleTeacherDto findById(Long id);

    List<GetAllTeachersDto> getAll();

    String insert (InsertTeacherDto dto);

    String updateHasReceivedTheFee (Long teacherId, Boolean hasReceivedTheFee);

    String delete(Long id);

    Tuple getTuple(String name);
}
