package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "medicine_inventory_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineInventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;
    @Column(nullable = false)
    private String medicineName;
    @Column(nullable = false)
    private Integer medicineQuantity;
    @Column(nullable = false)
    private String medicineSerialNumber;
    @ColumnDefault("25")
    private Float price;
}
