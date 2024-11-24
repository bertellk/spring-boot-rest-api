package com.berkaytell.controller;

import com.berkaytell.dto.course.GetAllCoursesDto;
import com.berkaytell.dto.course.GetSingleCourseDto;
import com.berkaytell.dto.course.InsertCourseDto;
import com.berkaytell.result.DataResult;
import com.berkaytell.result.Result;
import com.berkaytell.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/course/")
@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("get-all")
    ResponseEntity<DataResult<List<GetAllCoursesDto>>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    ResponseEntity<DataResult<GetSingleCourseDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PostMapping("insert")
    ResponseEntity<Result> insert(@RequestBody InsertCourseDto insertCourseDto) {
        return ResponseEntity.ok(courseService.insert(insertCourseDto));
    }

    @PostMapping("delete/{id}")
    ResponseEntity<Result> delete(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.delete(id));
    }

}
