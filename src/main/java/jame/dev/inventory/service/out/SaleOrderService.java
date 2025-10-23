package jame.dev.inventory.service.out;

import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.repo.ISaleOrderRepository;
import jame.dev.inventory.service.in.ISaleOrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SaleOrderService implements ISaleOrderService {
   @Autowired
   private final ISaleOrderRepository repo;

   @Override
   public List<SaleOrderEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<SaleOrderEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public SaleOrderEntity save(SaleOrderEntity order) {
      return repo.save(order);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
