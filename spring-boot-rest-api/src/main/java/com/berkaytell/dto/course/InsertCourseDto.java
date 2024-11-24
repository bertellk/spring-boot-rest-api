package com.berkaytell.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsertCourseDto {
    private String name;
    private Integer duration;
    private Integer quota;
}
