package jame.dev.inventory.service.out;

import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.repo.ISaleRepository;
import jame.dev.inventory.service.in.SaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class SaleServiceImp implements SaleService {

   private final ISaleRepository repo;

   public SaleServiceImp(ISaleRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<SaleEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<SaleEntity> getSaleById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public SaleEntity save(SaleEntity sale) {
      return repo.save(sale);
   }

   @Override
   @Transactional
   public void deleteSaleById(Long id) {
      repo.deleteById(id);
   }
}
