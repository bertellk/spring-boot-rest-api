package com.berkaytell.service.course;

import com.berkaytell.dto.course.GetAllCoursesDto;
import com.berkaytell.dto.course.GetSingleCourseDto;
import com.berkaytell.dto.course.InsertCourseDto;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;

import java.util.List;

public interface CourseService {

    DataResult<List<GetAllCoursesDto>> getAll();

    DataResult<GetSingleCourseDto> getById(Long id);

    Result insert(InsertCourseDto dto);

    Result delete(Long id);

    boolean existsById(Long id);

    boolean canAssignStudent(Long id);

    boolean isStudentAgeValid(Long courseId, Integer studentAge);
}
