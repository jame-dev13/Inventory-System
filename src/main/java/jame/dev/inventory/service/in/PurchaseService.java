package jame.dev.inventory.service.in;

import jame.dev.inventory.models.PurchaseEntity;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
   List<PurchaseEntity> getAll();
   Optional<PurchaseEntity> getUserById(Long id);
   PurchaseEntity save(PurchaseEntity purchase);
   void deleteUserById(Long id);
}
