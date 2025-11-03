package jame.dev.inventory.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "sale_orders")
public class SaleOrderEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany(cascade = {CascadeType.REFRESH})
   @JoinTable(name = "product_sell_orders",
           joinColumns = @JoinColumn(name = "id_order"),
           inverseJoinColumns = @JoinColumn(name = "id_product",
                   foreignKey = @ForeignKey(name = "fk_order_product",
                           foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products (id)")))
   private List<ProductEntity> products = new ArrayList<>();

   @ManyToOne
   @JoinColumn(name = "id_customer",
           foreignKey = @ForeignKey(name = "fk_order_customer",
                   foreignKeyDefinition = "FOREIGN KEY (id_customer) REFERENCES customers (id)"))
   private CustomerEntity customer;

   @Column(name = "order_cost", precision = 10, scale = 2, nullable = false)
   @Setter(AccessLevel.NONE)
   private BigDecimal orderCost;

   @PrePersist
   @PreUpdate
   private void calculateOrderCost(){
      this.orderCost = products.stream()
              .map(ProductEntity::getUnitPrice)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
   }

   @Column(name = "active", nullable = false)
   @Setter(AccessLevel.NONE)
   private boolean active = true;
}
