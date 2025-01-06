package com.berkaytell.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder // newlerken istediğimiz alanları set etmek için kullanılır
@SQLDelete(sql = "UPDATE students SET is_deleted = true WHERE id=?") // repo.delete() metodu çağrılınca update atar
@SQLRestriction(value = "is_deleted = false") // findById ve findAll metodunda silinenlerin gelmemesi için

public class Student extends BaseEntity {
    // Veritabanındaki Karşılığı Olan Yer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String nationalityNumber;
    private String city;
    private Integer age;
    private Boolean hasPayTheFee = false;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
