package jame.dev.inventory.restController.priv.admin;


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
@RequestMapping("${app.mapping.admin}")
@PreAuthorize("hasRole('ADMIN')")
public class CustomerControllerAdmin {

   private final CustomerService customerService;
   private final DtoMapper<CustomerDto, CustomerEntity> mapper;

   public CustomerControllerAdmin(CustomerService customerService, DtoMapper<CustomerDto, CustomerEntity> mapper) {
      this.customerService = customerService;
      this.mapper = mapper;
   }

   @GetMapping("/customers")
   public ResponseEntity<List<CustomerDto>> getCustomers() {
      List<CustomerDto> customersDtoList = customerService.getAll()
              .stream()
              .map(mapper::mapToDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(customersDtoList);
   }

   @PostMapping("/addCustomer")
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

   @PatchMapping("/patchCustomer/{id}")
   public ResponseEntity<CustomerDto> patchCustomer(@PathVariable @Nonnull Long id, @RequestBody @Nonnull  CustomerDtoIn customerDto){
      CustomerEntity customer = customerService.getCustomerById(id)
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));

      customer.setName(customerDto.name());
      customer.setLastName(customerDto.lastName());
      customer.setEmail(customerDto.email());
      customer.setPhone(customerDto.phone());
      customer.setAge(customerDto.age());

      CustomerEntity customerPatched = customerService.save(customer);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.mapToDto(customerPatched));
   }

   @DeleteMapping("/dropCustomer/{id}")
   public ResponseEntity<Void> dropCustomer(@PathVariable @Nonnull Long id){
      customerService.deleteCustomerById(id);
      return ResponseEntity.noContent().build();
   }
}
