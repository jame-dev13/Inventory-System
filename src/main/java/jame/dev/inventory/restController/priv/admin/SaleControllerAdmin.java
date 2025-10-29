package jame.dev.inventory.restController.priv.admin;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.service.in.SaleService;
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
public class SaleControllerAdmin {

   private final SaleService saleService;

   public SaleControllerAdmin(SaleService saleService) {
      this.saleService = saleService;
   }

   public ResponseEntity<List<SaleDto>> getSales() {
      Function<List<ProductEntity>, List<ProductDto>> mapProductToDto = products ->
              products.stream()
                      .map(p -> ProductDto.builder()
                              .id(p.getId())
                              .description(p.getDescription())
                              .stock(p.getStock())
                              .idProvider(p.getProvider().getId())
                              .unitPrice(p.getUnitPrice())
                              .build())
                      .toList();

      Function<SaleEntity, List<SaleOrderDto>> mapOrderToDto = order ->
         order.getSaleOrders().stream()
                 .map(so -> SaleOrderDto.builder()
                         .id(so.getId())
                         .customer(CustomerDto.builder()
                                 .id(so.getCustomer().getId())
                                 .name(so.getCustomer().getName() + " " + so.getCustomer().getLastName())
                                 .email(so.getCustomer().getEmail())
                                 .phone(so.getCustomer().getPhone())
                                 .age(so.getCustomer().getAge())
                                 .build())
                         .products(mapProductToDto.apply(so.getProducts()))
                         .orderCost(so.getOrderCost())
                         .build())
                 .toList();


      List<SaleDto> saleDtoList = saleService.getAll()
              .stream()
              .map(s -> SaleDto.builder()
                      .id(s.getId())
                      .orders(mapOrderToDto.apply(s))
                      .employee(EmployeeDto.builder()
                              .id(s.getEmployee().getId())
                              .name("%s %s".formatted(s.getEmployee().getUser().getName(), s.getEmployee().getUser().getLastName()))
                              .jobTitle(s.getEmployee().getJobTitle())
                              .salary(s.getEmployee().getSalary())
                              .shift(s.getEmployee().getShift())
                              .build())
                      .saleDate(s.getSaleDate())
                      .saleCost(s.getTotalCost())
                      .build())
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(saleDtoList);
   }
}
