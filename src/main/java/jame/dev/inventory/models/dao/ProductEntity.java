package jame.dev.inventory.models.dao;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "products")
public class ProductEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "description", length = 120, nullable = false)
   @NotBlank
   @Nonnull
   private String description;

   @Column(name = "stock", nullable = false)
   private int stock;

   @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
   @Nonnull
   private BigDecimal unitPrice;

   @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = ProviderEntity.class)
   @JoinColumn(name = "id_provider",
           foreignKey = @ForeignKey(
                   name = "fk_provider_prod",
                   foreignKeyDefinition = "FOREIGN KEY (id_provider) REFERENCES providers(id) ON DELETE SET NULL ON UPDATE CASCADE"))
   private ProviderEntity provider;

   @Column(name = "active", nullable = false)
   @Setter(AccessLevel.NONE)
   private boolean active = true;
   @PrePersist
   void setActive(){
      this.active = true;
   }
}
