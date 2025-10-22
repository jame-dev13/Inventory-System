package jame.dev.inventory.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "sales")
public class SaleEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany
   @JoinTable(name = "sell_orders",
           joinColumns = @JoinColumn(name = "id_sale"),
           inverseJoinColumns = @JoinColumn(name = "id_order"))
   private List<SaleOrderEntity> saleOrders;

   @OneToOne
   private EmployeeEntity employee;

   @Column(name = "sale_date", nullable = false)
   private LocalDate saleDate;

   @Column(name = "total_cost", precision = 10, scale = 2, nullable = false)
   private BigDecimal totalCost;
}
