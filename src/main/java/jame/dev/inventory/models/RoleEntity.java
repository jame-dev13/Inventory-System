package jame.dev.inventory.models;

import jakarta.persistence.*;
import jame.dev.inventory.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "role", length = 10, nullable = false)
   @Enumerated(EnumType.STRING)
   private ERole role;
}
