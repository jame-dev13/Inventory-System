package jame.dev.inventory.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrderEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany
   @JoinTable(name = "order_products",
           joinColumns = @JoinColumn(name = "id_order"),
           inverseJoinColumns = @JoinColumn(name = "id_product"))
   private List<ProductEntity> products = new ArrayList<>();

   @Column(name = "order_cost", precision = 10, scale = 2, nullable = false)
   @Nonnull
   private BigDecimal orderCost;
}
