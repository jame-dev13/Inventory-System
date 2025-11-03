package jame.dev.inventory.models;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jame.dev.inventory.models.enums.EShift;
import lombok.*;

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
                   foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE"))
   @Nonnull
   private UserEntity user;

   @Column(name = "salary", nullable = false, precision = 10, scale = 2)
   private BigDecimal salary;

   @Column(name = "shift", nullable = false, length = 12)
   @NotBlank
   @Nonnull
   @Enumerated(EnumType.STRING)
   private EShift shift;

   @Column(name = "active", nullable = false)
   @Setter(AccessLevel.NONE)
   private boolean active = true;
   @PrePersist
   private void setActive(){
      this.active = true;
   }

   public String getFullName(){
      return this.user.getName() + this.user.getLastName();
   }
}
