package jame.dev.inventory.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "name", length = 80, nullable = false)
   private String name;
   @Column(name = "last_name", length = 80, nullable = false)
   private String lastName;
   @Column(name = "email", unique = true, length = 100, nullable = false)
   @Email
   private String email;
   @Column(name = "password", nullable = false)
   private String password;
   @Column(name = "token", unique = true, length = 6, nullable = false)
   private String token;
   @Column(name = "verified", nullable = false)
   private boolean verified;

   @OneToMany
   @JoinTable(name = "user_roles",
           joinColumns = @JoinColumn(name = "id_user"),
           inverseJoinColumns = @JoinColumn(name = "id_role", unique = true))
   private Set<RoleEntity> roles;

   @OneToMany
   @JoinTable(name = "user_permissions",
           joinColumns = @JoinColumn(name = "id_user"),
           inverseJoinColumns = @JoinColumn(name = "id_permission", unique = true))
   private Set<PermissionEntity> permissions;
}

