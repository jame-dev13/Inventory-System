package jame.dev.inventory.service.out;

import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.repo.ICustomerRepository;
import jame.dev.inventory.service.in.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {
   @Autowired
   private final ICustomerRepository repo;

   @Override
   public List<CustomerEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<CustomerEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public CustomerEntity save(CustomerEntity customer) {
      return repo.save(customer);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
