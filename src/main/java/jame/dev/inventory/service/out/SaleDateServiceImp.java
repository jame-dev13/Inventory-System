package jame.dev.inventory.service.out;

import jame.dev.inventory.models.dao.SaleDateEntity;
import jame.dev.inventory.repo.SaleDateRepository;
import jame.dev.inventory.service.in.SaleDateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleDateServiceImp implements SaleDateService {
   private final SaleDateRepository repo;

   public SaleDateServiceImp(SaleDateRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<SaleDateEntity> getAll() {
      return repo.findAllAsc();
   }

   @Transactional
   @Override
   public SaleDateEntity save(SaleDateEntity sale) {
      return repo.save(sale);
   }

   @Override
   public Optional<SaleDateEntity> getSaleDateById(LocalDate localDate) {
      return repo.findById(localDate);
   }

   @Override
   public List<String> getEmployeesWhoSellByDate(LocalDate date) {
      return repo.findEmployeeNameOnSellDate(date);
   }

   @Override
   public BigDecimal getTotalAmountSellByDate(LocalDate date) {
      return repo.findTotalAmountByDate(date);
   }
}
