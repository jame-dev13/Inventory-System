package jame.dev.inventory.restController.priv;

import jame.dev.inventory.dtos.sale.in.SaleDtoIn;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.service.in.SaleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.mapping}/sales")
public class SaleController {

   private final SaleService saleService;
   private final DtoMapper<SaleDto, SaleEntity> saleMapper;
   private final EntityMapper<SaleEntity, SaleDtoIn> entitySaleMapper;

   public SaleController(SaleService saleService, DtoMapper<SaleDto, SaleEntity> saleMapper, EntityMapper<SaleEntity, SaleDtoIn> entitySaleMapper) {
      this.saleService = saleService;
      this.saleMapper = saleMapper;
      this.entitySaleMapper = entitySaleMapper;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<SaleDto>> getSales() {
      List<SaleDto> saleDtoList = saleService.getAll()
              .stream()
              .map(saleMapper::mapToDto)
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleDtoList);
   }

   @PreAuthorize("hasRole('EMPLOYEE')")
   @PostMapping
   public ResponseEntity<SaleDto> addSale(@RequestBody SaleDtoIn saleDtoIn){
      SaleEntity saleEntity = saleService.save(entitySaleMapper.mapToEntity(saleDtoIn));
      SaleDto saleDtoResponse = saleMapper.mapToDto(saleEntity);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleDtoResponse);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteSale(@PathVariable Long id){
      saleService.deleteSaleById(id);
      return ResponseEntity.noContent().build();
   }
}
