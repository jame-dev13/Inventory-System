package jame.dev.inventory.restController.priv;

import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.dtos.sale.in.SaleDtoIn;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.exceptions.SaleNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.dao.SaleDateEntity;
import jame.dev.inventory.models.dao.SaleEntity;
import jame.dev.inventory.service.in.SaleDateService;
import jame.dev.inventory.service.in.SaleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.SALES;

@RestController
@RequestMapping("${app.mapping}/sales")
public class SaleController {

   private final SaleService saleService;
   private final SaleDateService saleDateService;
   private final OutputMapper<SaleDto, SaleEntity> saleMapper;
   private final InputMapper<SaleEntity, SaleDtoIn> entitySaleMapper;
   private final Cache<SaleDto> cache;

   public SaleController(SaleService saleService, SaleDateService saleDateService, OutputMapper<SaleDto, SaleEntity> saleMapper, InputMapper<SaleEntity, SaleDtoIn> entitySaleMapper, Cache<SaleDto> cache) {
      this.saleService = saleService;
      this.saleDateService = saleDateService;
      this.saleMapper = saleMapper;
      this.entitySaleMapper = entitySaleMapper;
      this.cache = cache;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<SaleDto>> getSales() {
      Optional<List<SaleDto>> cacheList = cache.getCache(SALES.getName());
      if (cacheList.isPresent()) {
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(cacheList.get());
      }
      List<SaleDto> saleDtoList = saleService.getAll()
              .stream()
              .map(saleMapper::toDto)
              .toList();
      cache.saveCache(SALES.getName(), saleDtoList);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleDtoList);
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping("/{id}")
   public ResponseEntity<SaleDto> getSaleById(@PathVariable Long id) {
      SaleEntity saleEntity = saleService.getSaleById(id)
              .orElseThrow(() -> new SaleNotFoundException("Sale not found."));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleMapper.toDto(saleEntity));
   }

   @PreAuthorize("hasRole('EMPLOYEE')")
   @PostMapping
   public ResponseEntity<SaleDto> addSale(@RequestBody SaleDtoIn saleDtoIn) {
      SaleEntity saleEntity = saleService.save(entitySaleMapper.inputToEntity(saleDtoIn));
      saveDateSale(saleEntity);
      SaleDto saleDtoResponse = saleMapper.toDto(saleEntity);
      cache.addData(SALES.getName(), saleDtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleDtoResponse);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
      SaleEntity saleEntity = saleService.getSaleById(id)
              .orElseThrow(() -> new SaleNotFoundException("Sale not found."));
      SaleDto saleDto = saleMapper.toDto(saleEntity);
      cache.removeData(SALES.getName(), p -> p.id().equals(id), saleDto);
      saleService.deleteSaleById(id);
      return ResponseEntity.noContent().build();
   }

   private void saveDateSale(SaleEntity saleEntity) {
      SaleDateEntity saleDate = saleDateService.getSaleDateById(saleEntity.getSaleDate())
              .orElse(SaleDateEntity.builder()
                      .saleDate(saleEntity.getSaleDate())
                      .sales(new ArrayList<>())
                      .build());

      saleDate.getSales().add(saleEntity);
      saleDateService.save(saleDate);
   }
}
