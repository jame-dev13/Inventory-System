package jame.dev.inventory.restController.priv;


import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.customer.in.CustomerDtoIn;
import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.exceptions.CustomerNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
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
   private final OutputMapper<CustomerDto, CustomerEntity> mapperOut;
   private final InputMapper<CustomerEntity, CustomerDtoIn> mapperIn;

   public CustomerController(CustomerService customerService, OutputMapper<CustomerDto, CustomerEntity> mapperOut, InputMapper<CustomerEntity, CustomerDtoIn> mapperIn) {
      this.customerService = customerService;
      this.mapperOut = mapperOut;
      this.mapperIn = mapperIn;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<CustomerDto>> getCustomers() {
      List<CustomerDto> customersDtoList = customerService.getAll()
              .stream()
              .map(mapperOut::toDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(customersDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PostMapping
   public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDtoIn customerDto){
      CustomerEntity customerSaved = customerService.save(mapperIn.inputToEntity(customerDto));

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(customerSaved));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<CustomerDto> patchCustomer(@PathVariable @Nonnull Long id, @RequestBody @Nonnull  CustomerDtoIn customerDto){
      CustomerEntity customer = customerService.getCustomerById(id)
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));

      CustomerEntity customerPatched = customerService.update(customer, customerDto);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(customerPatched));
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropCustomer(@PathVariable @Nonnull Long id){
      customerService.deleteCustomerById(id);
      return ResponseEntity.noContent().build();
   }
}
