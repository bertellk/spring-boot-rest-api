package com.berkaytell.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSingleStudentDto {
    private String name;
    private String lastName;
    private String city;
    private Boolean hasPayTheFee;
}
