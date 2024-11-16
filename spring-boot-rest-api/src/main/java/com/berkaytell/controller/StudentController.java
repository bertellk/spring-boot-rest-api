package com.berkaytell.controller;

import com.berkaytell.dto.student.GetAllStudentsDto;
import com.berkaytell.dto.student.GetSingleStudentDto;
import com.berkaytell.dto.student.InsertStudentDto;
import com.berkaytell.service.student.StudentService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student/")
public class StudentController {
    // Security gibi ortak kullanılan servisler de yazılabilir
    private final StudentService studentService;

    // PathVariable: URL sonuna parametre alır /123 gibi
    // RequestParam: URL sonuna parametreyi şu şekilde alır -> /student?id=123
    // RequestBody: JSON olarak data alır

    //@GetMapping("get-by-id/{studentId}/{requestId}")
    // ResponseEntity<GetSingleStudentDto> findById(@PathVariable(name = "studentId") Long id, @PathVariable(name = "requestId") Long reqId)

    // ResponseEntity dönmemizin sebebi htpp protokolü içindir. 404, 200, 500 gibi.
    @GetMapping("get-by-id/{id}")
    ResponseEntity<GetSingleStudentDto> findById(@PathVariable(required = false) Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @GetMapping("get-all")
    ResponseEntity<List<GetAllStudentsDto>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @PostMapping("insert")
    ResponseEntity<String> insert(@RequestBody InsertStudentDto insertDto) {
        return ResponseEntity.ok(studentService.insert(insertDto));
    }

    @PostMapping("update-has-pay-the-fee/{id}")
    ResponseEntity<String> updateHasPayTheFee(@PathVariable Long id, @RequestParam Boolean paymentStatus) {
        return ResponseEntity.ok(studentService.updateHasPayTheFee(id, paymentStatus));
    }

    @GetMapping("delete/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.delete(id));
    }

    @GetMapping("get-tuple")
    ResponseEntity<Tuple> getTuple(@RequestParam String name) {
        return ResponseEntity.ok(studentService.getTuple(name));
    }

}
