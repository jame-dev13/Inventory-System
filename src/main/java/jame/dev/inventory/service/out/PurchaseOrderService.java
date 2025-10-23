package jame.dev.inventory.service.out;

import jame.dev.inventory.models.PurchaseOrderEntity;
import jame.dev.inventory.repo.IPurchaseOrderRepository;
import jame.dev.inventory.service.in.IPurchaseOrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {
   @Autowired
   private final IPurchaseOrderRepository repo;

   @Override
   public List<PurchaseOrderEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<PurchaseOrderEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public PurchaseOrderEntity save(PurchaseOrderEntity order) {
      return repo.save(order);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
