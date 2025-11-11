package jame.dev.inventory.service.in;

import jame.dev.inventory.models.dao.SaleEntity;
import jame.dev.inventory.models.dao.SaleOrderEntity;

import java.util.List;
import java.util.Optional;

public interface SaleService {
   List<SaleEntity> getAll();
   Optional<SaleEntity> getSaleById(Long id);
   SaleEntity save(SaleEntity sale);
   List<SaleOrderEntity> getOrdersById(Long id);
   void deleteSaleById(Long id);
}
