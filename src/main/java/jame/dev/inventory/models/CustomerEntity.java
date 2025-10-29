package jame.dev.inventory.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

   public String getFullName(){
      return this.getName() + " " + this.getLastName();
   }
}
