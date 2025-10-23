package jame.dev.inventory.service.out;

import jame.dev.inventory.models.PurchaseEntity;
import jame.dev.inventory.repo.IPurchaseRepository;
import jame.dev.inventory.service.in.IPurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PurchaseService implements IPurchaseService {
   @Autowired
   private final IPurchaseRepository repo;

   @Override
   public List<PurchaseEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<PurchaseEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public PurchaseEntity save(PurchaseEntity purchase) {
      return repo.save(purchase);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
