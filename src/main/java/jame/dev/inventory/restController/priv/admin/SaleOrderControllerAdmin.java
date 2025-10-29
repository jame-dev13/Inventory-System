package jame.dev.inventory.restController.priv.admin;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.service.in.SaleOrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("${app.mapping.admin}")
@PreAuthorize("hasRole('ADMIN')")
public class SaleOrderControllerAdmin {

   private final SaleOrderService saleOrderService;

   public SaleOrderControllerAdmin(SaleOrderService saleOrderService) {
      this.saleOrderService = saleOrderService;
   }

   public ResponseEntity<List<SaleOrderDto>> getSaleOrdes(){
      Function<SaleOrderEntity, List<ProductDto>> mapToDto = order ->
              order.getProducts().stream()
                      .map(p -> ProductDto.builder()
                              .id(p.getId())
                              .description(p.getDescription())
                              .stock(p.getStock())
                              .idProvider(p.getProvider().getId())
                              .unitPrice(p.getUnitPrice())
                              .build())
                      .toList();

      List<SaleOrderDto> orderDtoList = saleOrderService.getAll()
              .stream()
              .map(soe -> SaleOrderDto.builder()
                      .id(soe.getId())
                      .products(mapToDto.apply(soe))
                      .customer(CustomerDto.builder()
                              .id(soe.getId())
                              .name(soe.getCustomer().getName() + " " + soe.getCustomer().getLastName())
                              .email(soe.getCustomer().getEmail())
                              .phone(soe.getCustomer().getPhone())
                              .age(soe.getCustomer().getAge())
                              .build())
                      .orderCost(soe.getOrderCost())
                      .build())
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(orderDtoList);
   }
}
