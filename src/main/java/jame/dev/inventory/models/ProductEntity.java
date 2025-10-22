package jame.dev.inventory.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
   private String description;

   @Column(name = "stock", nullable = false)
   private int stock;

   @Column(name = "unit_price", precision = 10, scale = 2)
   private BigDecimal unitPrice;

   @OneToOne
   private ProviderEntity provider;
}
