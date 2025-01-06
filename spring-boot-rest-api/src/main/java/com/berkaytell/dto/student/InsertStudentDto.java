package com.berkaytell.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsertStudentDto {
    private Integer age;
    private String city;
    private String name;
    private String lastName;
    private String nationalityNumber;
    private String email;
}
