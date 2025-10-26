package jame.dev.inventory.service.in;

import jame.dev.inventory.models.SaleEntity;

import java.util.List;
import java.util.Optional;

public interface SaleService {
   List<SaleEntity> getAll();
   Optional<SaleEntity> getUserById(Long id);
   SaleEntity save(SaleEntity sale);
   void deleteUserById(Long id);
}
