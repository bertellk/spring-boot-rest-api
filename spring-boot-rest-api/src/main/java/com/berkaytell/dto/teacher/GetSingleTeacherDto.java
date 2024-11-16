package com.berkaytell.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSingleTeacherDto {
    private String name;
    private String lastName;
    private Integer age;
    private Boolean hasReceivedTheFee;
    //Buradaki özellikler istediğinde tek öğretmen için gelecek.

}
