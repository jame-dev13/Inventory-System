package jame.dev.inventory.service.in;

import jame.dev.inventory.dtos.employee.in.EmployeeDtoIn;
import jame.dev.inventory.models.dao.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
   List<EmployeeEntity> getAll();
   Optional<EmployeeEntity> getEmployeeById(Long id);
   EmployeeEntity save(EmployeeEntity employee);
   EmployeeEntity update(Long id, EmployeeDtoIn employeeDtoIn);
   void deleteEmployeeById(Long id);
}
