package jame.dev.inventory.service.in;

import jame.dev.inventory.dtos.sale.in.SaleOrderInDto;
import jame.dev.inventory.models.dao.SaleOrderEntity;

import java.util.List;
import java.util.Optional;

public interface SaleOrderService {
   List<SaleOrderEntity> getAll();
   Optional<SaleOrderEntity> getSaleOrderById(Long id);
   SaleOrderEntity save(SaleOrderEntity order);
   SaleOrderEntity update(SaleOrderEntity order, SaleOrderInDto orderSaleDto);
   List<SaleOrderEntity> getAllByIds(List<Long> ids);
   void deleteSaleOrderById(Long id);
}
