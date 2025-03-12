package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bill_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id")
    private PatientEntity patientEntity;
    private String billDetails;
}
