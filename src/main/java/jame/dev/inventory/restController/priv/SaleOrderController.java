package jame.dev.inventory.restController.priv;

import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.in.OrderSaleInDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.exceptions.SaleOrderNotFoundException;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.service.in.CustomerService;
import jame.dev.inventory.service.in.SaleOrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.mapping}/sale-orders")
public class SaleOrderController {

   private final SaleOrderService saleOrderService;
   private final DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper;
   private final EntityMapper<SaleOrderEntity, OrderSaleInDto> entitySaleOrderMapper;
   private final EntityMapper<ProductEntity, ProductDto> entityProductMapper;
   private final CustomerService customerService;

   public SaleOrderController(SaleOrderService saleOrderService, DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper, EntityMapper<SaleOrderEntity, OrderSaleInDto> entitySaleOrderMapper, EntityMapper<ProductEntity, ProductDto> entityProductMapper, CustomerService customerService) {
      this.saleOrderService = saleOrderService;
      this.saleOrderMapper = saleOrderMapper;
      this.entitySaleOrderMapper = entitySaleOrderMapper;
      this.entityProductMapper = entityProductMapper;
      this.customerService = customerService;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<SaleOrderDto>> getSaleOrdes() {
      List<SaleOrderDto> orderDtoList = saleOrderService.getAll()
              .stream()
              .map(saleOrderMapper::mapToDto)
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(orderDtoList);
   }

   @PreAuthorize("hasRole('EMPLOYEE')")
   @PostMapping
   public ResponseEntity<SaleOrderDto> addOrder(@RequestBody OrderSaleInDto orderSaleInDto) {
      SaleOrderEntity saleOrderEntity = saleOrderService
              .save(entitySaleOrderMapper.mapToEntity(orderSaleInDto));
      SaleOrderDto saleOrderDtoResponse = saleOrderMapper.mapToDto(saleOrderEntity);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleOrderDtoResponse);
   }

   @PreAuthorize("hasRole('EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<SaleOrderDto> patchOrder(@PathVariable Long id, @RequestBody OrderSaleInDto saleInDto) {
      SaleOrderEntity saleOrderEntity = saleOrderService.getSaleOrderById(id)
              .orElseThrow(() -> new SaleOrderNotFoundException("Order not found."));

      SaleOrderEntity saleOrderPatched = saleOrderService.update(saleOrderEntity, saleInDto);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleOrderMapper.mapToDto(saleOrderPatched));
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropOrder(@PathVariable Long id) {
      saleOrderService.deleteSaleOrderById(id);
      return ResponseEntity.noContent().build();
   }
}
