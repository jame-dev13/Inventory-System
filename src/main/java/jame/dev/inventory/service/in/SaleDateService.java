package jame.dev.inventory.service.in;

import jame.dev.inventory.models.dao.SaleDateEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleDateService {
   List<SaleDateEntity> getAll();
   SaleDateEntity save(final SaleDateEntity sale);
   Optional<SaleDateEntity> getSaleDateById(final LocalDate localDate);
   List<String> getEmployeesWhoSellByDate(final LocalDate date);
   BigDecimal getTotalAmountSellByDate(final LocalDate date);
}
