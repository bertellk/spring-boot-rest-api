package com.berkaytell.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teachNum;

    private String name;
    private String lastName;
    private String nationalityNumber;
    private String city;
    private Integer age; //bu sayede null olsa bile problem olmaz
    private Boolean hasReceivedTheFee;
    private Boolean isDeleted; //bilgiler asla silinmez.

}
