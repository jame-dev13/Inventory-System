package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.in.SaleOrderInDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.exceptions.CustomerNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.service.in.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleOrderMapper implements
        OutputMapper<SaleOrderDto, SaleOrderEntity>,
        InputMapper<SaleOrderEntity, SaleOrderInDto> {

   private final OutputMapper<ProductDto, ProductEntity> productMapper;
   private final OutputMapper<CustomerDto, CustomerEntity> customerMapper;
   private final CustomerService customerService;

   public SaleOrderMapper(OutputMapper<ProductDto, ProductEntity> productMapper, OutputMapper<CustomerDto, CustomerEntity> customerMapper, CustomerService customerService) {
      this.productMapper = productMapper;
      this.customerMapper = customerMapper;
      this.customerService = customerService;
   }


   @Override
   public SaleOrderEntity inputToEntity(SaleOrderInDto dto) {
      CustomerEntity customerEntity = customerService.getCustomerById(dto.customerId())
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
      return SaleOrderEntity.builder()
              .products(getProductEntityList(dto.productList()))
              .customer(customerEntity)
              .active(true)
              .build();
   }

   @Override
   public SaleOrderDto toDto(SaleOrderEntity entity) {
      return SaleOrderDto.builder()
              .id(entity.getId())
              .products(entity.getProducts().stream()
                      .map(productMapper::toDto)
                      .toList())
              .customer(customerMapper.toDto(entity.getCustomer()))
              .orderCost(entity.getOrderCost())
              .build();
   }

   @Override
   public SaleOrderEntity toEntity(SaleOrderDto dto) {
      return SaleOrderEntity.builder()
              .id(dto.id())
              .products(getProductEntityList(dto.products()))
              .customer(customerMapper.toEntity(dto.customer()))
              .active(true)
              .build();
   }

   private List<ProductEntity> getProductEntityList(List<ProductDto> listDto){
      return listDto.stream()
              .map(productMapper::toEntity)
              .collect(Collectors.toList());
   }
}
