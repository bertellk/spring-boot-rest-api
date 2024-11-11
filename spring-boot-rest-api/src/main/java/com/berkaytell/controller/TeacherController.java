package com.berkaytell.controller;

import com.berkaytell.dto.GetAllTeachersDto;
import com.berkaytell.dto.GetSingleTeacherDto;
import com.berkaytell.service.StudentService.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teacher/") //isimlendirme
public class TeacherController {
//Bu kontrolcülerde ortak kullanılan servisleri de yazabilirsin.
    private final TeacherService teacherService;

    @GetMapping("get-by-teachNum/{teachNum}")
    ResponseEntity<GetSingleTeacherDto>findById(@PathVariable(required = false) Long teachNum) {
        return ResponseEntity.ok(teacherService.findById(teachNum));

    }
    @GetMapping("get-all")
    ResponseEntity<List<GetAllTeachersDto>>getAll(){
        return ResponseEntity.ok(teacherService.getAll());
    }

}
