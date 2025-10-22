package jame.dev.inventory.models;


import jakarta.persistence.*;
import jame.dev.inventory.models.enums.EJobTitle;
import jame.dev.inventory.models.enums.EShift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "employees")
public class EmployeeEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @OneToOne(cascade = CascadeType.ALL)
   private UserEntity user;

   @Column(name = "job_title", unique = true, nullable = false)
   @Enumerated(EnumType.STRING)
   private EJobTitle jobTitle;

   @Column(name = "salary", nullable = false, precision = 10, scale = 2)
   private BigDecimal salary;

   @Column(name = "shift", nullable = false, length = 12)
   @Enumerated(EnumType.STRING)
   private EShift shift;
}
