package jame.dev.inventory.service.out;

import jame.dev.inventory.models.PurchaseOrderEntity;
import jame.dev.inventory.repo.IPurchaseOrderRepository;
import jame.dev.inventory.service.in.PurchaseOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class PurchaseOrderServiceImp implements PurchaseOrderService {
   private final IPurchaseOrderRepository repo;

   public PurchaseOrderServiceImp(IPurchaseOrderRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<PurchaseOrderEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<PurchaseOrderEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public PurchaseOrderEntity save(PurchaseOrderEntity order) {
      return repo.save(order);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
