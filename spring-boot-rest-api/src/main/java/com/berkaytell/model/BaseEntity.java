package com.berkaytell.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseEntity {
    private boolean isDeleted = false; // kalıcı olarak silme (ama dümenden)
    private boolean isActive = true; // instagram hesap dondurdun
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime lastUpdateTime;
    private Long createUser;
}