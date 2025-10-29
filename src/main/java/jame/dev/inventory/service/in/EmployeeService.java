package jame.dev.inventory.service.in;

import jame.dev.inventory.models.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
   List<EmployeeEntity> getAll();
   Optional<EmployeeEntity> getEmployeeById(Long id);
   EmployeeEntity save(EmployeeEntity employee);
   void deleteEmployeeById(Long id);
}
