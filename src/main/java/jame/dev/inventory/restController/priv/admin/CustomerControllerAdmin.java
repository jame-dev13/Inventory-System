package jame.dev.inventory.restController.priv.admin;


import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.customer.in.CustomerDtoIn;
import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.exceptions.CustomerNotFoundException;
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

   public CustomerControllerAdmin(CustomerService customerService) {
      this.customerService = customerService;
   }

   @GetMapping("/customers")
   public ResponseEntity<List<CustomerDto>> getCustomers() {
      List<CustomerDto> customersDtoList = customerService.getAll()
              .stream()
              .map(c -> CustomerDto.builder()
                      .id(c.getId())
                      .name(c.getName() + " " + c.getLastName())
                      .email(c.getEmail())
                      .phone(c.getPhone())
                      .age(c.getAge())
                      .build())
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(customersDtoList);
   }

   @PostMapping("/addCustomer")
   public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDtoIn customerDto){
      CustomerEntity customer = customerService.save(
              CustomerEntity.builder()
                      .name(customerDto.name())
                      .lastName(customerDto.lastName())
                      .email(customerDto.email())
                      .phone(customerDto.phone())
                      .age(customerDto.age())
                      .build()
      );

      CustomerDto customerResponseDto = CustomerDto
              .builder()
              .id(customer.getId())
              .name(customer.getName() + " " + customer.getLastName())
              .email(customer.getEmail())
              .phone(customer.getPhone())
              .age(customer.getAge())
              .build();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(customerResponseDto);
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
      CustomerDto customerDtoResponse = CustomerDto
              .builder()
              .id(customerPatched.getId())
              .name(customerPatched.getName() + " "+ customerPatched.getLastName())
              .email(customerPatched.getEmail())
              .phone(customerPatched.getPhone())
              .age(customerPatched.getAge())
              .build();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(customerDtoResponse);
   }

   @DeleteMapping("/dropCustomer/{id}")
   public ResponseEntity<Void> dropCustomer(@PathVariable @Nonnull Long id){
      customerService.deleteCustomerById(id);
      return ResponseEntity.noContent().build();
   }
}
