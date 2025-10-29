package jame.dev.inventory.service.in;

import jame.dev.inventory.models.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
   List<CustomerEntity> getAll();
   Optional<CustomerEntity> getCustomerById(Long id);
   CustomerEntity save(CustomerEntity customer);
   void deleteCustomerById(Long id);
}
