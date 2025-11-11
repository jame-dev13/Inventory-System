package jame.dev.inventory.models.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "sale_dates")
public class SaleDateEntity {

   @Id
   private LocalDate saleDate;

   @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
   @JoinTable(name = "sale_date_sales",
           joinColumns = @JoinColumn(name = "id_date"),
           inverseJoinColumns = @JoinColumn(name = "id_sale"))
   private List<SaleEntity> sales;
}
