package jame.dev.inventory.service.out;

import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.in.OrderSaleInDto;
import jame.dev.inventory.exceptions.CustomerNotFoundException;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.repo.ISaleOrderRepository;
import jame.dev.inventory.service.in.CustomerService;
import jame.dev.inventory.service.in.SaleOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleOrderServiceImp implements SaleOrderService {

   private final ISaleOrderRepository repo;
   private final CustomerService customerService;
   private final EntityMapper<ProductEntity, ProductDto> productEntityMapper;

   public SaleOrderServiceImp(ISaleOrderRepository repo, CustomerService customerService, EntityMapper<ProductEntity, ProductDto> productEntityMapper) {
      this.repo = repo;
      this.customerService = customerService;
      this.productEntityMapper = productEntityMapper;
   }

   @Override
   public List<SaleOrderEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<SaleOrderEntity> getSaleOrderById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public SaleOrderEntity save(SaleOrderEntity order) {
      return repo.save(order);
   }

   @Override
   @Transactional
   public SaleOrderEntity update(SaleOrderEntity order, OrderSaleInDto orderSaleDto) {
      CustomerEntity customerEntity = customerService.getCustomerById(orderSaleDto.customerId())
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));

      order.setProducts(orderSaleDto.productList()
              .stream()
              .map(productEntityMapper::mapToEntity)
              .collect(Collectors.toList()));
      order.setCustomer(customerEntity);
      order.setOrderCost(orderSaleDto.productList()
              .stream()
              .map(ProductDto::unitPrice)
              .reduce(BigDecimal.ZERO, BigDecimal::add));
      return repo.save(order);
   }

   @Override
   @Transactional
   public void deleteSaleOrderById(Long id) {
      repo.deleteById(id);
   }
}
