package jame.dev.inventory.restController.priv.admin;

import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.service.in.SaleOrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.mapping.admin}")
@PreAuthorize("hasRole('ADMIN')")
public class SaleOrderControllerAdmin {

   private final SaleOrderService saleOrderService;
   private final DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper;

   public SaleOrderControllerAdmin(SaleOrderService saleOrderService, DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper) {
      this.saleOrderService = saleOrderService;
      this.saleOrderMapper = saleOrderMapper;
   }

   public ResponseEntity<List<SaleOrderDto>> getSaleOrdes(){
      List<SaleOrderDto> orderDtoList = saleOrderService.getAll()
              .stream()
              .map(saleOrderMapper::mapToDto)
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(orderDtoList);
   }
}
