package com.berkaytell.dto.course;

import com.berkaytell.dto.student.GetSingleStudentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSingleCourseDto {
    private Integer quota;
    private Integer duration;
    private String name;
    private String teacherName;

    //private List<GetSingleStudentDto> students; // Burada Eğer Student entity'si verirsek stackOwerFLow hatası alırız.
}
