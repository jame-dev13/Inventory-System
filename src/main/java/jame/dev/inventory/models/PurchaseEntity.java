package jame.dev.inventory.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "purchases")
public class PurchaseEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany
   @JoinTable(name = "purchase_orders",
           joinColumns = @JoinColumn(name = "id_purchase"),
           inverseJoinColumns = @JoinColumn(name = "id_order"))
   private List<PurchaseOrderEntity> purchases;

   @Column(name = "purchase_date", nullable = false)
   private LocalDate purchaseDate;

   @OneToOne
   private EmployeeEntity employee;
}
