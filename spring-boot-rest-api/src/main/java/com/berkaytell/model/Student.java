package com.berkaytell.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    // Veritabanındaki Karşılığı Olan Yer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String nationalityNumber;
    private String city;
    private Integer age;
    private Boolean hasPayTheFee;
    private Boolean isDeleted; // Veri Tabanından Veri Silinirken True Olarak Setlenir.
}
