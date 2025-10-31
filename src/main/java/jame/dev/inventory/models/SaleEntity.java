package jame.dev.inventory.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

   @ManyToMany(cascade =  {CascadeType.REMOVE, CascadeType.REFRESH},
           targetEntity = SaleOrderEntity.class)
   @JoinTable(name = "sell_orders",
           joinColumns = @JoinColumn(name = "id_sale"),
           inverseJoinColumns = @JoinColumn(name = "id_order",
           foreignKey = @ForeignKey(name = "fk_sale_order",
           foreignKeyDefinition = "FOREIGN KEY (id_order) REFERENCES sale_orders(id) ON DELETE CASCADE ON UPDATE CASCADE")))
   private List<SaleOrderEntity> saleOrders = new ArrayList<>();

   @OneToOne
   @JoinColumn(name = "id_employee",
   foreignKey = @ForeignKey(name = "fk_sale_employee",
   foreignKeyDefinition = "FOREIGN KEY (id_employee) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE"))
   private EmployeeEntity employee;

   @Column(name = "sale_date", nullable = false)
   private LocalDate saleDate;

   @Column(name = "total_cost", precision = 10, scale = 2, nullable = false)
   private BigDecimal totalCost;
}
