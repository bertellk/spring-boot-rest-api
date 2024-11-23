package com.berkaytell.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class InsertTeacherDto {

    private String city;
    private String name;
    private String lastName;
}
