package jame.dev.inventory.service.in;

import jame.dev.inventory.dtos.sale.in.OrderSaleInDto;
import jame.dev.inventory.models.SaleOrderEntity;

import java.util.List;
import java.util.Optional;

public interface SaleOrderService {
   List<SaleOrderEntity> getAll();
   Optional<SaleOrderEntity> getSaleOrderById(Long id);
   SaleOrderEntity save(SaleOrderEntity order);
   SaleOrderEntity update(SaleOrderEntity order, OrderSaleInDto orderSaleDto);
   void deleteSaleOrderById(Long id);
}
