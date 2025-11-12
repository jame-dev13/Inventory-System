package jame.dev.inventory.repo;

import jame.dev.inventory.models.dao.SaleEntity;
import jame.dev.inventory.models.dao.SaleOrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends CustomJpaRepository<SaleEntity, Long> {

   @Query(value = "SELECT s FROM SaleOrderEntity s JOIN s.saleOrder sa WHERE sa.id = :orderId", nativeQuery = true)
   List<SaleOrderEntity> getSaleOrders(@Param("orderId") Long orderId);
}
