package jame.dev.inventory.service.in;

import jame.dev.inventory.models.SaleOrderEntity;

import java.util.List;
import java.util.Optional;

public interface SaleOrderService {
   List<SaleOrderEntity> getAll();
   Optional<SaleOrderEntity> getUserById(Long id);
   SaleOrderEntity save(SaleOrderEntity order);
   void deleteUserById(Long id);
}
