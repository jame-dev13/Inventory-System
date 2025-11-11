package jame.dev.inventory.models.dao;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "sales")
public class SaleEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
   @JoinTable(name = "sell_orders",
           joinColumns = @JoinColumn(name = "id_sale"),
           inverseJoinColumns = @JoinColumn(name = "id_order",
           foreignKey = @ForeignKey(name = "fk_sale_order",
           foreignKeyDefinition = "FOREIGN KEY (id_order) REFERENCES sale_orders(id)")))
   private List<SaleOrderEntity> saleOrders = new ArrayList<>();

   @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
   @JoinColumn(name = "id_employee", nullable = false,
   foreignKey = @ForeignKey(name = "fk_sale_employee",
   foreignKeyDefinition = "FOREIGN KEY (id_employee) REFERENCES employees(id)"))
   private EmployeeEntity employee;

   @Column(name = "sale_date", nullable = false)
   @Setter(AccessLevel.NONE)
   private LocalDate saleDate;

   @Setter(AccessLevel.NONE)
   @Column(name = "total_cost", precision = 10, scale = 2, nullable = false)
   private BigDecimal totalCost;

   @PrePersist
   @PreUpdate
   private void calculateTotalCost(){
      final double discount = 0.15;
      final double charge = 0.20;
      BigDecimal totalOrderCost = this.saleOrders.stream()
              .map(SaleOrderEntity::getOrderCost)
              .reduce(BigDecimal.ZERO, BigDecimal::add);

      if(totalOrderCost.doubleValue() > 1000d){
         BigDecimal discountApplied = totalOrderCost.multiply(BigDecimal.valueOf(discount));
         this.totalCost = totalOrderCost.subtract(discountApplied);
         return;
      }
      BigDecimal chargeApplied = totalOrderCost.multiply(BigDecimal.valueOf(charge));
      this.totalCost = totalOrderCost.add(chargeApplied);
   }

   @Column(name = "active", nullable = false)
   @Setter(AccessLevel.NONE)
   private boolean active = true;
}
