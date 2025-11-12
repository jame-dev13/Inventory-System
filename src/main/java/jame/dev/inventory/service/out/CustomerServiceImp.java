package jame.dev.inventory.service.out;

import jame.dev.inventory.dtos.customer.in.CustomerDtoIn;
import jame.dev.inventory.models.dao.CustomerEntity;
import jame.dev.inventory.repo.CustomerRepository;
import jame.dev.inventory.service.in.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceImp implements CustomerService {

   private final CustomerRepository repo;
   public CustomerServiceImp(CustomerRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<CustomerEntity> getAll() {
      return repo.findAllActives();
   }

   @Override
   public Optional<CustomerEntity> getCustomerById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public CustomerEntity save(CustomerEntity customer) {
      return repo.save(customer);
   }

   @Override
   @Transactional
   public CustomerEntity update(CustomerEntity customer, CustomerDtoIn customerDto) {
      customer.setName(customerDto.name());
      customer.setLastName(customerDto.lastName());
      customer.setEmail(customerDto.email());
      customer.setPhone(customerDto.phone());
      customer.setAge(customerDto.age());
      return repo.save(customer);
   }

   @Override
   @Transactional
   public void deleteCustomerById(Long id) {
      repo.softDeleteById(id);
   }
}
