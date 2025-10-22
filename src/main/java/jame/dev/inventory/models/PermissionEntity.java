package jame.dev.inventory.models;

import jakarta.persistence.*;
import jame.dev.inventory.models.enums.EPermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "permissions")
public class PermissionEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "permission", length = 10, nullable = false)
   @Enumerated(EnumType.STRING)
   private EPermission permission;

}
