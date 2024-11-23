package com.berkaytell.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SQLDelete(sql = "UPDATE teachers SET is_active = false WHERE id=?")
@SQLRestriction(value = "is_active = true")
//Bu kısımda hata var mı bakalım. Active kısmını silinince false olsun diye updateledim.
//Yani en azından onu yapmak istedim xd
//Bir de bu entityde isdeleted zaten kendi değeri olarak var o problem oluyo mu?
public class Teacher extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String nationalityNumber;
    private String city;
    private Integer age; //bu sayede null olsa bile problem olmaz
    private Boolean hasReceivedTheFee;
    private Boolean isDeleted; //bilgiler asla silinmez.

}
