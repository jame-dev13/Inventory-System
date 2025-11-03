package jame.dev.inventory.repo;

import jame.dev.inventory.models.SaleOrderEntity;

import java.util.List;

public interface ISaleOrderRepository extends CustomJpaRepository<SaleOrderEntity, Long> {

   List<SaleOrderEntity> findAllByIdIn(List<Long> id);
}
