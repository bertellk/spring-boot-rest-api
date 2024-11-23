package com.berkaytell.controller;

import com.berkaytell.dto.teacher.GetAllTeachersDto;
import com.berkaytell.dto.teacher.GetSingleTeacherDto;
import com.berkaytell.dto.teacher.InsertTeacherDto;
import com.berkaytell.service.teacher.TeacherService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("insert")
    ResponseEntity<String> insert(@RequestBody InsertTeacherDto insertDto){
        return ResponseEntity.ok(teacherService.insert(insertDto));
    }
    @PostMapping("update-has-received-the-fee/{id}")
    ResponseEntity<String> updateHasReceivedTheFee(@PathVariable Long id,@RequestParam Boolean receivedStatus){
        return ResponseEntity.ok(teacherService.updateHasReceivedTheFee(id,receivedStatus));
    }
    @GetMapping("delete/{id}")
    ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.delete(id));
    }
    @GetMapping("get-tuple")
    ResponseEntity<Tuple> getTuple(@RequestParam String name){
        return ResponseEntity.ok(teacherService.getTuple(name));
    }

}
