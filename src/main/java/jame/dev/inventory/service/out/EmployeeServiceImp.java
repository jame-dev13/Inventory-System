package jame.dev.inventory.service.out;

import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.repo.IEmployeeRepository;
import jame.dev.inventory.service.in.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {

   private final IEmployeeRepository repo;
   public EmployeeServiceImp(IEmployeeRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<EmployeeEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<EmployeeEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public EmployeeEntity save(EmployeeEntity employee) {
      return repo.save(employee);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
