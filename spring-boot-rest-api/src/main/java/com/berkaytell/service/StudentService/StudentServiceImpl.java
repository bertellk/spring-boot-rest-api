package com.berkaytell.service.StudentService;

import com.berkaytell.dto.GetAllStudentsDto;
import com.berkaytell.dto.GetSingleStudentDto;
import com.berkaytell.mapper.StudentMapper;
import com.berkaytell.model.Student;
import com.berkaytell.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public GetSingleStudentDto findById(Long id) {

        if (Objects.isNull(id))
            throw new IllegalArgumentException("Id Boş Bırakılamaz");

        Student student = studentRepository.findById(id).orElse(null);

        if (Objects.isNull(student))
            throw new IllegalArgumentException("Öğrenci Bulunamadı");
         // request response pattern
        return studentMapper.forResponse().map(student, GetSingleStudentDto.class);
    }

    @Override
    public List<GetAllStudentsDto> getAll() {
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(student -> studentMapper.forResponse().map(student, GetAllStudentsDto.class))
                .toList();
    }
}
