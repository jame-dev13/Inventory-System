package jame.dev.inventory.models;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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

   @OneToMany
   @JoinTable(name = "purchase_orders",
           joinColumns = @JoinColumn(name = "id_purchase"),
           inverseJoinColumns = @JoinColumn(name = "id_order", unique = true))
   private List<PurchaseOrderEntity> purchases = new ArrayList<>();

   @Column(name = "purchase_date", nullable = false)
   @Nonnull
   private LocalDate purchaseDate;

   @OneToOne
   @Nonnull
   private EmployeeEntity employee;
}
