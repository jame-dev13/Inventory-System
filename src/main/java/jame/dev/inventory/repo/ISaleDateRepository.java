package jame.dev.inventory.repo;

import jame.dev.inventory.models.dao.SaleDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ISaleDateRepository extends JpaRepository<SaleDateEntity, LocalDate> {

   @Query(value = "SELECT e FROM SaleDateEntity e ORDER BY e.saleDate ASC")
   List<SaleDateEntity> findAllAsc();

   @Query(value = """
           SELECT CONCAT(u.name, ' ', u.lastName) FROM SaleDateEntity sde
           JOIN sde.sales s
           JOIN s.employee e
           JOIN e.user u
           WHERE sde.saleDate = :date
           """)
   List<String> findEmployeeNameOnSellDate(@Param("date") final LocalDate date);

   @Query(value = """
           SELECT SUM(s.totalCost) AS total FROM SaleDateEntity as sde
           JOIN sde.sales s
           WHERE sde.saleDate = :date
           """)
   BigDecimal findTotalAmountByDate(@Param("date") final LocalDate date);
}
