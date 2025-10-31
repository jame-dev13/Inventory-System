package jame.dev.inventory.restController.priv;


import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.customer.in.CustomerDtoIn;
import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.exceptions.CustomerNotFoundException;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.service.in.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.mapping}/customers")
public class CustomerController {

   private final CustomerService customerService;
   private final DtoMapper<CustomerDto, CustomerEntity> mapper;

   public CustomerController(CustomerService customerService, DtoMapper<CustomerDto, CustomerEntity> mapper) {
      this.customerService = customerService;
      this.mapper = mapper;
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<CustomerDto>> getCustomers() {
      List<CustomerDto> customersDtoList = customerService.getAll()
              .stream()
              .map(mapper::mapToDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(customersDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PostMapping
   public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDtoIn customerDto){
      CustomerEntity customerSaved = customerService.save(
              CustomerEntity.builder()
                      .name(customerDto.name())
                      .lastName(customerDto.lastName())
                      .email(customerDto.email())
                      .phone(customerDto.phone())
                      .age(customerDto.age())
                      .build()
      );

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.mapToDto(customerSaved));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<CustomerDto> patchCustomer(@PathVariable @Nonnull Long id, @RequestBody @Nonnull  CustomerDtoIn customerDto){
      CustomerEntity customer = customerService.getCustomerById(id)
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));

      CustomerEntity customerPatched = customerService.update(customer, customerDto);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.mapToDto(customerPatched));
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropCustomer(@PathVariable @Nonnull Long id){
      customerService.deleteCustomerById(id);
      return ResponseEntity.noContent().build();
   }
}
