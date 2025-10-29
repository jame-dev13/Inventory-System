package jame.dev.inventory.models;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

   @OneToOne
   @JoinColumn(name = "user_id",
           foreignKey =
           @ForeignKey(name = "fk_user_id",
                   foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE"))
   @Nonnull
   private UserEntity user;

   @Column(name = "job_title", unique = true, nullable = false)
   @NotBlank
   @Nonnull
   @Enumerated(EnumType.STRING)
   private EJobTitle jobTitle;

   @Column(name = "salary", nullable = false, precision = 10, scale = 2)
   private BigDecimal salary;

   @Column(name = "shift", nullable = false, length = 12)
   @NotBlank
   @Nonnull
   @Enumerated(EnumType.STRING)
   private EShift shift;
}
