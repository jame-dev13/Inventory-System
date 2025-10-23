package jame.dev.inventory.service.out;

import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.repo.IEmployeeRepository;
import jame.dev.inventory.service.in.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {

   @Autowired
   private final IEmployeeRepository repo;

   @Override
   public List<EmployeeEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<EmployeeEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public EmployeeEntity save(EmployeeEntity employee) {
      return repo.save(employee);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
