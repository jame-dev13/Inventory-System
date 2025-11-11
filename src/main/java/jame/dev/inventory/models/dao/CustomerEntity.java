package jame.dev.inventory.models.dao;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", length = 80, nullable = false)
   @NotBlank
   private String name;

   @Column(name = "last_name", length = 80, nullable = false)
   @NotBlank
   private String lastName;

   @Column(name = "email", length = 80, nullable = false, unique = true)
   @NotBlank
   @Nonnull
   @Email
   private String email;

   @Column(name = "phone", nullable = false, unique = true)
   @NotBlank
   @Nonnull
   private BigInteger phone;

   @Column(name = "age", nullable = false)
   @NotBlank
   private int age;

   @Column(name = "active", nullable = false)
   @Setter(AccessLevel.NONE)
   private boolean active = true;
   @PrePersist
   private void setActive(){
      this.active = true;
   }

   public String getFullName(){
      return this.getName() + " " + this.getLastName();
   }
}
