package com.berkaytell.service.student;

import com.berkaytell.configuration.ConstantMessage;
import com.berkaytell.dto.student.GetAllStudentsDto;
import com.berkaytell.dto.student.GetSingleStudentDto;
import com.berkaytell.dto.student.InsertStudentDto;
import com.berkaytell.mapper.StudentMapper;
import com.berkaytell.model.Course;
import com.berkaytell.model.Student;
import com.berkaytell.repository.StudentRepository;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;
import com.berkaytell.service.course.CourseService;
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
    private final CourseService courseService;

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
    public DataResult<GetSingleStudentDto> findByIdDataResult(Long id) {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("Id Boş Bırakılamaz");

        Student student = studentRepository.findById(id).orElse(null);

        if (Objects.isNull(student))
            throw new IllegalArgumentException("Öğrenci Bulunamadı");
        // request response pattern
        GetSingleStudentDto dto = studentMapper.forResponse().map(student, GetSingleStudentDto.class);
        return DataResult.of(dto, true);
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
        //Mapper üstünde olma sebebi zaten 11 haneli değilse hiç bakmayalım diye.

        if(dto.getNationalityNumber()==null || dto.getNationalityNumber().length() != 11){
            return "Türkiye Cumhuriyeti Kimlik Numarası 11 haneli olmalıdır!";
        }
        //Aynı Tc var ise kayıt olmaz.
        if(studentRepository.existsByNationalityNumber(dto.getNationalityNumber())!=null){
            return "Zaten kayıtlı";
        }

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
        Student studentToUpdate  = studentRepository.findById(studentId).orElse(null);

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

    @Override
    public Result insertResult(InsertStudentDto dto) {
        Student studentToInsert = studentMapper.forResponse().map(dto, Student.class);

        try {
            studentRepository.save(studentToInsert);
            return Result.of(true);
        } catch (DataAccessException e) {
            return Result.of(false);
        }
    }

    @Override
    public void detachStudentsFromCourse(Long courseId) {
        List<Student> students = studentRepository.findByCourseId(courseId);
        students.forEach(s -> s.setCourse(null));
    }

    @Override
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    @Override
    public Integer getAgeById(Long id) {
        return studentRepository.getAgeById(id);
    }

    @Override
    public boolean getHasPayTheFeeById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);

        // students null ise false döndürür, değilse student'in has pay the fee alanını döndürün
        return student != null && student.getHasPayTheFee();
    }

    @Override
    public Result assignStudent(Long studentId, Long courseId) {
        if (!checkIfAssignable(studentId, courseId))
            Result.of(false, "Atama Yapılamaz");

        Student student = studentRepository.findById(studentId).orElse(null);
        //atama kısmı
        student.setCourse(Course.builder().id(courseId).build());
        studentRepository.save(student);

        //varmı, yaşı yeto mu, kurs kotası dolu mu, ödeme yaptı mı,
        return Result.of(true, "Öğrenci Atandı");
    }

    private boolean checkIfAssignable(Long studentId, Long courseId) {
        if (!courseService.existsById(courseId))
            return false;

        if (!existsById(studentId))
            return false;

        Student student = studentRepository.findById(studentId).orElse(null);
        // elimizde kursun minAge, ya öğrenci servisten studentId ile o öğrencinin yaşını çekcez
        if (!courseService.isStudentAgeValid(courseId, student.getAge()))
            return false;
        // ya da minAge'i ve studentId'yi, studentService göndercez

        if (!getHasPayTheFeeById(studentId))
            return false;

        return courseService.canAssignStudent(courseId);
    }

}
