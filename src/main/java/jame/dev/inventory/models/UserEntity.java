package jame.dev.inventory.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
   @NotBlank
   private String name;

   @Column(name = "last_name", length = 80, nullable = false)
   @NotBlank
   private String lastName;

   @Column(name = "email", unique = true, length = 100, nullable = false)
   @Email
   @NotBlank
   @Nonnull
   private String email;

   @Column(name = "password", nullable = false)
   @NotBlank
   @Nonnull
   private String password;

   @Column(name = "token", unique = true, length = 6, nullable = false)
   @Nonnull
   @NotBlank
   private String token;

   @Column(name = "verified", nullable = false)
   private boolean verified;

   @OneToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class,
           cascade = CascadeType.PERSIST, orphanRemoval = true)
   @JoinTable(name = "user_roles",
           joinColumns = @JoinColumn(name = "id_user", unique = true,
           foreignKey = @ForeignKey(name = "fk_user_users",
                   foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE")),
           inverseJoinColumns = @JoinColumn(name = "id_role"))
   private Set<RoleEntity> roles = new HashSet<>();
}

