package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements DtoMapper<EmployeeDto, EmployeeEntity> {
   @Override
   public EmployeeDto mapToDto(EmployeeEntity entity) {
      return EmployeeDto.builder()
              .id(entity.getId())
              .name("%s %s".formatted(entity.getUser().getName(), entity.getUser().getLastName()))
              .jobTitle(entity.getJobTitle())
              .salary(entity.getSalary())
              .shift(entity.getShift())
              .build();
   }
}
