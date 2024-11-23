package com.berkaytell.service.teacher;

import com.berkaytell.configuration.ConstantMessage;
import com.berkaytell.dto.teacher.GetAllTeachersDto;
import com.berkaytell.dto.teacher.GetSingleTeacherDto;
import com.berkaytell.dto.teacher.InsertTeacherDto;
import com.berkaytell.mapper.TeacherMapper;
import com.berkaytell.model.Teacher;
import com.berkaytell.repository.TeacherRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;


    @Override
    public GetSingleTeacherDto findById(Long id) {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("id boş olamaz.");

        Teacher teacher = teacherRepository.findById(id).orElse(null);

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

    @Override
    public String insert(InsertTeacherDto dto) {
        Teacher teacherToInsert = teacherMapper.forResponse().map(dto, Teacher.class);
        try {
            teacherRepository.save(teacherToInsert);
            return ConstantMessage.MESSAGE_SUCCESS;
        }catch (DataAccessException e){
            return ConstantMessage.MESSAGE_FAILURE;
        }
    }

    @Override
    public String updateHasReceivedTheFee(Long teacherId, Boolean hasReceivedTheFee){
        //Öğretmeni bul
        Teacher teacherToUpdate = teacherRepository.findById(teacherId).orElse(null);

        //öğretmen null ise succes : false
        if(teacherToUpdate == null)
            return ConstantMessage.TEACHER_NOT_FOUND;

        //Set etme kısmı
        teacherToUpdate.setHasReceivedTheFee(hasReceivedTheFee);
        //save kısmı
        teacherRepository.save(teacherToUpdate);
        return ConstantMessage.MESSAGE_SUCCESS;
    }

    @Override
    //Burada soft delete yapıyoruz
    public String delete(Long id) {
        Teacher teacherToDelete = teacherRepository.findById(id).orElse(null);
    if(teacherToDelete == null){
        return ConstantMessage.TEACHER_NOT_FOUND;
    }
         teacherRepository.delete(teacherToDelete);

        return ConstantMessage.MESSAGE_SUCCESS;
    }

    @Override
    public Tuple getTuple(String name) {
        Tuple tuple = teacherRepository.findByCustomName(name);
        Teacher.builder()
                .age((Integer) tuple.get("age"))
                .build();
        return teacherRepository.findByCustomName(name);
    }

}
