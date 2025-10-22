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
@Table(name = "providers")
public class ProviderEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", length = 80, nullable = false, unique = true)
   private String name;

   @Column(name = "cellphone", unique = true, nullable = false)
   private BigInteger cellphone;

   @Column(name = "email", unique = true, nullable = false)
   @Email
   private String email;
}
