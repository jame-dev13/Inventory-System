package jame.dev.inventory.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
   private String name;
   @Column(name = "last_name", length = 80, nullable = false)
   private String lastName;
   @Column(name = "email", length = 80, nullable = false, unique = true)
   @Email
   private String email;
   @Column(name = "phone", nullable = false, unique = true)
   private BigInteger phone;
   @Column(name = "age", nullable = false)
   private int age;
}
