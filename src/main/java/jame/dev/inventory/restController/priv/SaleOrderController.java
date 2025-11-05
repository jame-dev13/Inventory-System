package jame.dev.inventory.restController.priv;

import jame.dev.inventory.cache.in.Cache;
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
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.SALE_ORDERS;

@RestController
@RequestMapping("${app.mapping}/sale-orders")
public class SaleOrderController {

   private final SaleOrderService saleOrderService;
   private final OutputMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper;
   private final InputMapper<SaleOrderEntity, SaleOrderInDto> entitySaleOrderMapper;
   private final Cache<SaleOrderDto> cache;

   public SaleOrderController(SaleOrderService saleOrderService, OutputMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper, InputMapper<SaleOrderEntity, SaleOrderInDto> entitySaleOrderMapper, Cache<SaleOrderDto> cache) {
      this.saleOrderService = saleOrderService;
      this.saleOrderMapper = saleOrderMapper;
      this.entitySaleOrderMapper = entitySaleOrderMapper;
      this.cache = cache;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<SaleOrderDto>> getSaleOrdes() {
      Optional<List<SaleOrderDto>> cacheList = cache.getCache(SALE_ORDERS.getName());
      if (cacheList.isPresent()) {
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(cacheList.get());
      }
      List<SaleOrderDto> orderDtoList = saleOrderService.getAll()
              .stream()
              .map(saleOrderMapper::toDto)
              .toList();
      cache.saveCache(SALE_ORDERS.getName(), orderDtoList);
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
      cache.addData(SALE_ORDERS.getName(), saleOrderDtoResponse);
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
      SaleOrderDto dtoResponse = saleOrderMapper.toDto(saleOrderPatched);
      cache.updateData(SALE_ORDERS.getName(), so -> so.id().equals(id), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropOrder(@PathVariable Long id) {
      SaleOrderEntity saleOrderEntity = saleOrderService.getSaleOrderById(id)
              .orElseThrow(() -> new SaleOrderNotFoundException("Sale order not found."));
      SaleOrderDto saleOrderDto = saleOrderMapper.toDto(saleOrderEntity);
      cache.removeData(SALE_ORDERS.getName(), so -> so.id().equals(id), saleOrderDto);
      saleOrderService.deleteSaleOrderById(id);
      return ResponseEntity.noContent().build();
   }
}
