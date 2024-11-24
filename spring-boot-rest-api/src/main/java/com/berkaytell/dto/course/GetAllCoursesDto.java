package com.berkaytell.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllCoursesDto {
    private Integer quota;
    private Integer duration;
    private String name;
}
