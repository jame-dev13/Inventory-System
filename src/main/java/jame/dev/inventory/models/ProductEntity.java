package jame.dev.inventory.models;

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

   @Column(name = "unit_price", precision = 10, scale = 2)
   @Nonnull
   private BigDecimal unitPrice;

   @OneToOne
   @NonNull
   private ProviderEntity provider;
}
