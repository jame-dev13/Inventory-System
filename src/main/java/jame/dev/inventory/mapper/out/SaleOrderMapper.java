package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class SaleOrderMapper implements DtoMapper<SaleOrderDto, SaleOrderEntity> {
   private final DtoMapper<ProductDto, ProductEntity> productMapper;
   private final DtoMapper<CustomerDto, CustomerEntity> customerMapper;

   public SaleOrderMapper(DtoMapper<ProductDto, ProductEntity> productMapper, DtoMapper<CustomerDto, CustomerEntity> customerMapper) {
      this.productMapper = productMapper;
      this.customerMapper = customerMapper;
   }

   @Override
   public SaleOrderDto mapToDto(SaleOrderEntity entity) {
      return SaleOrderDto.builder()
              .id(entity.getId())
              .customer(customerMapper.mapToDto(entity.getCustomer()))
              .products(entity.getProducts().stream()
                      .map(productMapper::mapToDto)
                      .toList())
              .orderCost(entity.getOrderCost())
              .build();
   }
}
