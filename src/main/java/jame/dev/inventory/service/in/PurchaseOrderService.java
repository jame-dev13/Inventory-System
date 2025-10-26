package jame.dev.inventory.service.in;

import jame.dev.inventory.models.PurchaseOrderEntity;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {
   List<PurchaseOrderEntity> getAll();
   Optional<PurchaseOrderEntity> getUserById(Long id);
   PurchaseOrderEntity save(PurchaseOrderEntity order);
   void deleteUserById(Long id);
}
