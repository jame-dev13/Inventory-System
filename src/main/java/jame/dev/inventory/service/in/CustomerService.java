package jame.dev.inventory.service.in;

import jame.dev.inventory.dtos.customer.in.CustomerDtoIn;
import jame.dev.inventory.models.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
   List<CustomerEntity> getAll();
   Optional<CustomerEntity> getCustomerById(Long id);
   CustomerEntity save(CustomerEntity customer);
   CustomerEntity update(CustomerEntity customer, CustomerDtoIn customerDto);
   void deleteCustomerById(Long id);
}
