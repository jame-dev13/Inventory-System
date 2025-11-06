package jame.dev.inventory.service.out;

import jame.dev.inventory.dtos.employee.in.EmployeeDtoIn;
import jame.dev.inventory.exceptions.EmployeeNotFoundException;
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
      return repo.findAllActives();
   }

   @Override
   public Optional<EmployeeEntity> getEmployeeById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public EmployeeEntity save(EmployeeEntity employee) {
      return repo.save(employee);
   }

   @Override
   @Transactional
   public EmployeeEntity update(Long id, EmployeeDtoIn employeeDtoIn) {
      EmployeeEntity employeeEntity = repo.findById(id)
              .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));
      employeeEntity.setSalary(employeeDtoIn.salary());
      employeeEntity.setShift(employeeDtoIn.shift());
      return repo.save(employeeEntity);
   }

   @Override
   @Transactional
   public void deleteEmployeeById(Long id) {
      repo.softDeleteById(id);
   }
}
