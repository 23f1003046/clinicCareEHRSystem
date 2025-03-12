package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "doctor_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UserEntity userEntity;
    private String phoneNumber;
    @ColumnDefault("300")
    private Float docFee;

    public DoctorEntity(Long doctorId, UserEntity userEntity) {
        this.doctorId = doctorId;
        this.userEntity = userEntity;
    }
}

