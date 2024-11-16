package com.berkaytell.service.student;

import com.berkaytell.configuration.ConstantMessage;
import com.berkaytell.dto.student.GetAllStudentsDto;
import com.berkaytell.dto.student.GetSingleStudentDto;
import com.berkaytell.dto.student.InsertStudentDto;
import com.berkaytell.mapper.StudentMapper;
import com.berkaytell.model.Student;
import com.berkaytell.repository.StudentRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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

    @Override
    public String insert(InsertStudentDto dto) {

        Student studentToInsert = studentMapper.forResponse().map(dto, Student.class);

        try {
            studentRepository.save(studentToInsert);
            return ConstantMessage.MESSAGE_SUCCESS;
        } catch (DataAccessException e) {
            return ConstantMessage.MESSAGE_FAILURE;
        }
    }

    @Override
    public String updateHasPayTheFee(Long studentId, Boolean hasPayTheFee) {
//        Optional<Student> optionalStudent = studentRepository.findById(studentId); //repository Optional<T> türünde veri döner
//
//        if (optionalStudent.isEmpty())

        // öğrenciyi bul
        Student studentToUpdate = studentRepository.findById(studentId).orElse(null);

        // öğrenci null ise succes : false
        if (studentToUpdate == null)
            return ConstantMessage.STUDENT_NOT_FOUND;

        // set etme kısmı
        studentToUpdate.setHasPayTheFee(hasPayTheFee);
        // save
        studentRepository.save(studentToUpdate);

        return ConstantMessage.MESSAGE_SUCCESS;
    }

    @Override
    // soft delete
    public String delete(Long id) {
        Student studentToDelete = studentRepository.findById(id).orElse(null);

        if (studentToDelete == null) {
            return ConstantMessage.STUDENT_NOT_FOUND;
        }

        studentRepository.delete(studentToDelete);

//        Student student = new Student();
//        Student student1 = Student.builder()
//                .age(12)
//                .hasPayTheFee(false)
//                .build();

        return ConstantMessage.MESSAGE_SUCCESS;
    }

    @Override
    public Tuple getTuple(String name) {
        Tuple tuple = studentRepository.findByCustomName(name);
        Student.builder()
                .age((Integer) tuple.get("age"))
                .build();
        return studentRepository.findByCustomName(name);
    }

}
