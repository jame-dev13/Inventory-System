package jame.dev.inventory.service.out;

import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.repo.ISaleRepository;
import jame.dev.inventory.service.in.ISaleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class SaleService implements ISaleService {
   @Autowired
   private final ISaleRepository repo;

   @Override
   public List<SaleEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<SaleEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public SaleEntity save(SaleEntity sale) {
      return repo.save(sale);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
