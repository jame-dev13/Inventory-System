package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SaleOrderEntityMapperOut implements EntityMapper<SaleOrderEntity, SaleOrderDto> {

   private final EntityMapper<ProductEntity, ProductDto> productEntityMapper;
   private final EntityMapper<CustomerEntity, CustomerDto> customerEntityMapper;

   public SaleOrderEntityMapperOut(EntityMapper<ProductEntity, ProductDto> productEntityMapper, EntityMapper<CustomerEntity, CustomerDto> customerEntityMapper) {
      this.productEntityMapper = productEntityMapper;
      this.customerEntityMapper = customerEntityMapper;
   }

   @Override
   public SaleOrderEntity mapToEntity(SaleOrderDto dto) {
      BigDecimal total = dto.products().stream()
              .map(ProductDto::unitPrice)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      return SaleOrderEntity.builder()
              .id(dto.id())
              .products(productEntityMappingHelper(dto.products()))
              .customer(customerEntityMapper.mapToEntity(dto.customer()))
              .orderCost(total)
              .build();
   }

   private List<ProductEntity> productEntityMappingHelper(List<ProductDto> dto){
      return dto.stream()
              .map(productEntityMapper::mapToEntity)
              .toList();
   }
}
