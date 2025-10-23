package jame.dev.inventory.service.in;

import jame.dev.inventory.models.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
   List<EmployeeEntity> getAll();
   Optional<EmployeeEntity> getUserById(Long id);
   EmployeeEntity save(EmployeeEntity employee);
   void deleteUserById(Long id);
}
