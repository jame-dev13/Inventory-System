package jame.dev.inventory.service.out;

import jame.dev.inventory.models.dao.SaleEntity;
import jame.dev.inventory.models.dao.SaleOrderEntity;
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
      return repo.findAllActives();
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
   public List<SaleOrderEntity> getOrdersById(Long id) {
      return repo.getSaleOrders(id);
   }

   @Override
   @Transactional
   public void deleteSaleById(Long id) {
      repo.softDeleteById(id);
   }
}
