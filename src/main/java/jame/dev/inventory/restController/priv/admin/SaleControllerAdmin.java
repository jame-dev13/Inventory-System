package jame.dev.inventory.restController.priv.admin;

import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.service.in.SaleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.mapping.admin}")
@PreAuthorize("hasRole('ADMIN')")
public class SaleControllerAdmin {

   private final SaleService saleService;
   private final DtoMapper<SaleDto, SaleEntity> saleMapper;

   public SaleControllerAdmin(SaleService saleService, DtoMapper<SaleDto, SaleEntity> saleMapper) {
      this.saleService = saleService;
      this.saleMapper = saleMapper;
   }


   @GetMapping("/sales")
   public ResponseEntity<List<SaleDto>> getSales() {
      List<SaleDto> saleDtoList = saleService.getAll()
              .stream()
              .map(saleMapper::mapToDto)
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleDtoList);
   }
}
