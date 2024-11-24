package com.berkaytell.service.course;

import com.berkaytell.dto.course.GetAllCoursesDto;
import com.berkaytell.dto.course.GetSingleCourseDto;
import com.berkaytell.dto.course.InsertCourseDto;
import com.berkaytell.mapper.CustomMapper;
import com.berkaytell.model.Course;
import com.berkaytell.repository.CourseRepository;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;
import com.berkaytell.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CustomMapper customMapper;

    @Override
    public DataResult<List<GetAllCoursesDto>> getAll() {
        List<Course> courseList = courseRepository.findAll(); // veriyi aldık

        List<GetAllCoursesDto> data = courseList.stream().map(course -> customMapper.forResponse().map(course, GetAllCoursesDto.class)).toList(); // dtoya çevirdik

        return DataResult.of(data, true, "Kurslar Listelendi.");
    }

    @Override
    public DataResult<GetSingleCourseDto> getById(Long id) {
        if (courseRepository.existsById(id))
            return DataResult.of(null, false, "Buluanamdı.");

        Course course = courseRepository.findById(id).orElse(null);
        GetSingleCourseDto dto = customMapper.forResponse().map(course, GetSingleCourseDto.class);

        return DataResult.of(dto, true);
    }

    @Override
    public Result insert(InsertCourseDto dto) {
        Course courseToInsert = customMapper.forRequest().map(dto, Course.class);

        courseRepository.save(courseToInsert);

        return Result.of(true);
    }

    @Override
    public Result delete(Long id) {
        if (!courseRepository.existsById(id))
            return Result.of(false, "Buluanamdı.");

        // circular dependency yediğimiz için comment'e aldım
        //studentService.detachStudentsFromCourse(id);

        Course course = courseRepository.findById(id).orElse(null);

        courseRepository.delete(course);

        return Result.of(true);
    }

    @Override
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

    @Override
    public boolean canAssignStudent(Long id) {
        Course course = courseRepository.findById(id).orElse(null);

        if (course == null)
            return false;

        return course.getStudents().size() < course.getQuota();
    }

    @Override
    public boolean isStudentAgeValid(Long courseId, Integer studentAge) {
        Course course = courseRepository.findById(courseId).orElse(null);

        if (course == null)
            return false;

        return studentAge >= course.getMinimumAge();
    }


}
