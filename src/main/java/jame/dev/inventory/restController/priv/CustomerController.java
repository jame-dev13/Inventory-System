package jame.dev.inventory.restController.priv;


import jakarta.annotation.Nonnull;
import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.dtos.customer.in.CustomerDtoIn;
import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.exceptions.CustomerNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.dao.CustomerEntity;
import jame.dev.inventory.service.in.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.CUSTOMERS;

@RestController
@RequestMapping("${app.mapping}/customers")
public class CustomerController {

   private final CustomerService customerService;
   private final OutputMapper<CustomerDto, CustomerEntity> mapperOut;
   private final InputMapper<CustomerEntity, CustomerDtoIn> mapperIn;
   private final Cache<CustomerDto> cache;

   public CustomerController(CustomerService customerService, OutputMapper<CustomerDto, CustomerEntity> mapperOut, InputMapper<CustomerEntity, CustomerDtoIn> mapperIn, Cache<CustomerDto> cache) {
      this.customerService = customerService;
      this.mapperOut = mapperOut;
      this.mapperIn = mapperIn;
      this.cache = cache;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<CustomerDto>> getCustomers() {
      Optional<List<CustomerDto>> optionalList = cache.getCache(CUSTOMERS.getName());
      if (optionalList.isPresent()) {
         System.out.println("retried for the cache.");
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(optionalList.get());
      }
      List<CustomerDto> customerDtoList = customerService.getAll()
              .stream()
              .map(mapperOut::toDto)
              .toList();
      cache.saveCache(CUSTOMERS.getName(), customerDtoList);
      System.out.println("saved in cache.");
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(customerDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping("/{id}")
   public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
      CustomerEntity customer = customerService.getCustomerById(id)
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(customer));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PostMapping
   public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDtoIn customerDtoIn) {
      CustomerEntity customerSaved = customerService.save(mapperIn.inputToEntity(customerDtoIn));
      CustomerDto customerDto = mapperOut.toDto(customerSaved);
      cache.addData(CUSTOMERS.getName(), customerDto);
      System.out.println("customer saved into cache");
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(customerSaved));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<CustomerDto> patchCustomer(@PathVariable @Nonnull Long id, @RequestBody @Nonnull CustomerDtoIn customerDto) {
      CustomerEntity customer = customerService.getCustomerById(id)
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
      CustomerEntity customerPatched = customerService.update(customer, customerDto);
      CustomerDto dtoResponse = mapperOut.toDto(customerPatched);
      cache.updateData(CUSTOMERS.getName(), f -> f.id().equals(id), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(customerPatched));
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropCustomer(@PathVariable @Nonnull Long id) {
      CustomerEntity customerEntity = customerService.getCustomerById(id)
              .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
      cache.removeData(CUSTOMERS.getName(), f -> f.id().equals(id), mapperOut.toDto(customerEntity));
      customerService.deleteCustomerById(id);
      return ResponseEntity.noContent().build();
   }
}
