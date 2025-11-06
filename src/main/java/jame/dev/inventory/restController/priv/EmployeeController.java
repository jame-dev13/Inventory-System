package jame.dev.inventory.restController.priv;

import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.dtos.employee.in.EmployeeDtoIn;
import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.exceptions.EmployeeNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.service.in.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.EMPLOYEES;

@RestController
@RequestMapping("${app.mapping}/employees")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class EmployeeController {
   private final EmployeeService employeeService;
   private final OutputMapper<EmployeeDto, EmployeeEntity> mapperOut;
   private final InputMapper<EmployeeEntity, EmployeeDtoIn> mapperIn;
   private final Cache<EmployeeDto> cache;

   public EmployeeController(EmployeeService employeeService, OutputMapper<EmployeeDto, EmployeeEntity> mapperOut, InputMapper<EmployeeEntity, EmployeeDtoIn> mapperIn, Cache<EmployeeDto> cache) {
      this.employeeService = employeeService;
      this.mapperOut = mapperOut;
      this.mapperIn = mapperIn;
      this.cache = cache;
   }

   @GetMapping
   public ResponseEntity<List<EmployeeDto>> getEmployees(){
      Optional<List<EmployeeDto>> cacheList = cache.getCache(EMPLOYEES.getName());
      if(cacheList.isPresent()){
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(cacheList.get());
      }
      List<EmployeeDto> dtoResponse = employeeService.getAll()
              .stream()
              .map(mapperOut::toDto)
              .toList();
      cache.saveCache(EMPLOYEES.getName(), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }
   @GetMapping("/{id}")
   public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id){
      EmployeeEntity employeeEntity = employeeService.getEmployeeById(id)
              .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));
      EmployeeDto employeeDto = mapperOut.toDto(employeeEntity);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(employeeDto);
   }
   @PostMapping
   public ResponseEntity<EmployeeDto> postEmployee(@RequestBody EmployeeDtoIn employeeDtoIn){
      EmployeeEntity employeeEntity = employeeService.save(mapperIn.inputToEntity(employeeDtoIn));
      EmployeeDto employeeDto = mapperOut.toDto(employeeEntity);
      cache.addData(EMPLOYEES.getName(), employeeDto);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(employeeDto);
   }
   @PatchMapping("/{id}")
   public ResponseEntity<EmployeeDto> patchEmployee(@PathVariable Long id, @RequestBody EmployeeDtoIn employeeDtoIn){
      EmployeeEntity employeeEntity = employeeService.update(id, employeeDtoIn);
      EmployeeDto employeeDto = mapperOut.toDto(employeeEntity);
      cache.updateData(EMPLOYEES.getName(), e -> e.id().equals(id), employeeDto);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(employeeDto);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
      EmployeeEntity employeeEntity = employeeService.getEmployeeById(id)
              .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));
      EmployeeDto employeeDto = mapperOut.toDto(employeeEntity);
      cache.removeData(EMPLOYEES.getName(), e -> e.id().equals(id), employeeDto);
      employeeService.deleteEmployeeById(id);
      return ResponseEntity.noContent().build();
   }
}
