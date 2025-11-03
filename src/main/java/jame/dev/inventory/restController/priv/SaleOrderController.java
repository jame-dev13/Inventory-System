package jame.dev.inventory.restController.priv;

import jame.dev.inventory.dtos.sale.in.SaleOrderInDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.exceptions.SaleOrderNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.SaleOrderEntity;
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
   private final OutputMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper;
   private final InputMapper<SaleOrderEntity, SaleOrderInDto> entitySaleOrderMapper;

   public SaleOrderController(SaleOrderService saleOrderService, OutputMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper, InputMapper<SaleOrderEntity, SaleOrderInDto> entitySaleOrderMapper) {
      this.saleOrderService = saleOrderService;
      this.saleOrderMapper = saleOrderMapper;
      this.entitySaleOrderMapper = entitySaleOrderMapper;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<SaleOrderDto>> getSaleOrdes() {
      List<SaleOrderDto> orderDtoList = saleOrderService.getAll()
              .stream()
              .map(saleOrderMapper::toDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(orderDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping("/{id}")
   public ResponseEntity<SaleOrderDto> getSaleOrderById(@PathVariable Long id) {
      SaleOrderEntity orderEntity = saleOrderService.getSaleOrderById(id)
              .orElseThrow(() -> new SaleOrderNotFoundException("Sale Order not found."));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleOrderMapper.toDto(orderEntity));
   }

   @PreAuthorize("hasRole('EMPLOYEE')")
   @PostMapping
   public ResponseEntity<SaleOrderDto> addOrder(@RequestBody SaleOrderInDto saleOrderInDto) {
      SaleOrderEntity saleOrderEntity = saleOrderService
              .save(entitySaleOrderMapper.inputToEntity(saleOrderInDto));
      SaleOrderDto saleOrderDtoResponse = saleOrderMapper.toDto(saleOrderEntity);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleOrderDtoResponse);
   }

   @PreAuthorize("hasRole('EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<SaleOrderDto> patchOrder(@PathVariable Long id, @RequestBody SaleOrderInDto saleInDto) {
      SaleOrderEntity saleOrderEntity = saleOrderService.getSaleOrderById(id)
              .orElseThrow(() -> new SaleOrderNotFoundException("Order not found."));

      SaleOrderEntity saleOrderPatched = saleOrderService.update(saleOrderEntity, saleInDto);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleOrderMapper.toDto(saleOrderPatched));
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropOrder(@PathVariable Long id) {
      saleOrderService.deleteSaleOrderById(id);
      return ResponseEntity.noContent().build();
   }
}
