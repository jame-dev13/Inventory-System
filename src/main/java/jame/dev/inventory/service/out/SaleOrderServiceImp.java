package jame.dev.inventory.service.out;

import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.repo.ISaleOrderRepository;
import jame.dev.inventory.service.in.SaleOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SaleOrderServiceImp implements SaleOrderService {

   private final ISaleOrderRepository repo;

   public SaleOrderServiceImp(ISaleOrderRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<SaleOrderEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<SaleOrderEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public SaleOrderEntity save(SaleOrderEntity order) {
      return repo.save(order);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
