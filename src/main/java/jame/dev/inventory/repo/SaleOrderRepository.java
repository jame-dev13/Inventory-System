package jame.dev.inventory.repo;

import jame.dev.inventory.models.dao.SaleOrderEntity;

import java.util.List;

public interface SaleOrderRepository extends CustomJpaRepository<SaleOrderEntity, Long> {

   List<SaleOrderEntity> findAllByIdIn(List<Long> id);
}
