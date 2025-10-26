package jame.dev.inventory.service.out;

import jame.dev.inventory.models.PurchaseEntity;
import jame.dev.inventory.repo.IPurchaseRepository;
import jame.dev.inventory.service.in.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class PurchaseServiceImp implements PurchaseService {

   private final IPurchaseRepository repo;

   public PurchaseServiceImp(IPurchaseRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<PurchaseEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<PurchaseEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public PurchaseEntity save(PurchaseEntity purchase) {
      return repo.save(purchase);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
