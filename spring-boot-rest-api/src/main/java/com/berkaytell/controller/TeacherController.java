package com.berkaytell.controller;

import com.berkaytell.dto.teacher.GetAllTeachersDto;
import com.berkaytell.dto.teacher.GetSingleTeacherDto;
import com.berkaytell.service.teacher.TeacherService;
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

    @GetMapping("get-by-teachNum/{id}")
    ResponseEntity<GetSingleTeacherDto>findById(@PathVariable(required = false) Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }
    @GetMapping("get-all")
    ResponseEntity<List<GetAllTeachersDto>>getAll(){
        return ResponseEntity.ok(teacherService.getAll());
    }

}
