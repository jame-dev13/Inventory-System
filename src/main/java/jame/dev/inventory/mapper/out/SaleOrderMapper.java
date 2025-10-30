package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.in.OrderSaleInDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.exceptions.CustomerNotFoundException;
import jame.dev.inventory.exceptions.ProductNotFoundException;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.service.in.CustomerService;
import jame.dev.inventory.service.in.ProductService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SaleOrderMapper implements
        DtoMapper<SaleOrderDto, SaleOrderEntity>,
        EntityMapper<SaleOrderEntity, OrderSaleInDto> {

   private final DtoMapper<ProductDto, ProductEntity> productMapper;
   private final DtoMapper<CustomerDto, CustomerEntity> customerMapper;
   private final EntityMapper<ProductEntity, ProductDto> entityProductMapper;
   private final CustomerService customerService;
   private final ProductService productService;

   public SaleOrderMapper(DtoMapper<ProductDto, ProductEntity> productMapper, DtoMapper<CustomerDto, CustomerEntity> customerMapper, EntityMapper<ProductEntity, ProductDto> entityProductMapper, CustomerService customerService, ProductService productService) {
      this.productMapper = productMapper;
      this.customerMapper = customerMapper;
      this.entityProductMapper = entityProductMapper;
      this.customerService = customerService;

      this.productService = productService;
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

   @Override
   public SaleOrderEntity mapToEntity(OrderSaleInDto dto) {
      return buildEntity(dto.productList(), dto.customerId());
   }


   private BigDecimal calculateTotal(List<ProductDto> productDtoList) {
      return productDtoList.stream()
              .map(ProductDto::unitPrice)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
   }

   private SaleOrderEntity buildEntity(List<ProductDto> products, Long id) {
      CustomerEntity customerEntity = customerService.getCustomerById(id)
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
      BigDecimal total = calculateTotal(products);
      return SaleOrderEntity.builder()
              .products(getProducts(products))
              .customer(customerEntity)
              .orderCost(total)
              .build();
   }

   private List<ProductEntity> getProducts(List<ProductDto> productsDto) {
      return productsDto.stream()
              .map(p -> productService.getProductById(p.id())
                      .orElseThrow(() -> new ProductNotFoundException("Product not found.")))
              .toList();
   }
}
