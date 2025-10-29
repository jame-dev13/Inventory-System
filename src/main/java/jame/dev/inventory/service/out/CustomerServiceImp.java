package jame.dev.inventory.service.out;

import jame.dev.inventory.models.CustomerEntity;
import jame.dev.inventory.repo.ICustomerRepository;
import jame.dev.inventory.service.in.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceImp implements CustomerService {

   private final ICustomerRepository repo;
   public CustomerServiceImp(ICustomerRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<CustomerEntity> getAll() {
      return repo.findAll();
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
   public void deleteCustomerById(Long id) {
      repo.deleteById(id);
   }
}
