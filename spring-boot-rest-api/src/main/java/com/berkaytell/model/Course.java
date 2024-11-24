package com.berkaytell.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder // newlerken istediğimiz alanları set etmek için kullanılır
@SQLDelete(sql = "UPDATE courses SET is_deleted = true WHERE id=?") // repo.delete() metodu çağrılınca update atar
@SQLRestriction(value = "is_deleted = false") // findById ve findAll metodunda silinenlerin gelmemesi için
public class Course extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quota;
    private Integer duration;
    private String name;
    private String teacherName; //TODO sonrasında Teacher Class'ına bağlanacak

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Student> students = List.of();

    private Integer minimumAge;
}
