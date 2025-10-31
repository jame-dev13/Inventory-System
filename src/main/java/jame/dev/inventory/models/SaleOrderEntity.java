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
@Table(name = "sale_orders")
public class SaleOrderEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.REMOVE},
           targetEntity = ProductEntity.class)
   @JoinTable(name = "product_sell_orders",
           joinColumns = @JoinColumn(name = "id_order"),
           inverseJoinColumns = @JoinColumn(name = "id_product",
                   foreignKey = @ForeignKey(name = "fk_order_product",
                           foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE")))
   private List<ProductEntity> products = new ArrayList<>();

   @OneToOne
   @JoinColumn(name = "id_customer",
           foreignKey = @ForeignKey(name = "fk_order_customer",
                   foreignKeyDefinition = "FOREIGN KEY (id_customer) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE"))
   @Nonnull
   private CustomerEntity customer;

   @Column(name = "order_cost", precision = 10, scale = 2, nullable = false)
   @Nonnull
   private BigDecimal orderCost;
}
