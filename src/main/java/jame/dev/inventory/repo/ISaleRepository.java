package jame.dev.inventory.repo;

import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISaleRepository extends CustomJpaRepository<SaleEntity, Long> {

   @Query(value = "SELECT s FROM SaleOrderEntity s JOIN s.saleOrder sa WHERE sa.id = :orderId", nativeQuery = true)
   List<SaleOrderEntity> getSaleOrders(@Param("orderId") Long orderId);
}
