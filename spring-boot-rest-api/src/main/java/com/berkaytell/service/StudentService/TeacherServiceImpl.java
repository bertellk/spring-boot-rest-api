package com.berkaytell.service.StudentService;

import com.berkaytell.dto.GetAllTeachersDto;
import com.berkaytell.dto.GetSingleTeacherDto;
import com.berkaytell.mapper.TeacherMapper;
import com.berkaytell.model.Teacher;
import com.berkaytell.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;



    @Override
    public GetSingleTeacherDto findById(Long teachNum) {
        if (Objects.isNull(teachNum))
            throw new IllegalArgumentException("teachNum boş olamaz.");

        Teacher teacher = teacherRepository.findById(teachNum).orElse(null);

        if (Objects.isNull(teacher))
            throw new IllegalArgumentException("Öğretmen bulunamadı.");
        //request response pattern
        return teacherMapper.forResponse().map(teacher, GetSingleTeacherDto.class);
    }

    @Override
    public List<GetAllTeachersDto> getAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        //Tüm öğretmenleri bulacak.

        return teachers.stream().map(teacher -> teacherMapper.forResponse().map(teacher, GetAllTeachersDto.class)).toList();
    }

    private void conflict(){
        //conflict
    }


}
