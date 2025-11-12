package jame.dev.inventory.models.dao;

import jakarta.persistence.*;
import jame.dev.inventory.models.enums.Role;
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
   private Long id;

   @Column(name = "role", length = 10, nullable = false)
   @Enumerated(EnumType.STRING)
   private Role role;
}
