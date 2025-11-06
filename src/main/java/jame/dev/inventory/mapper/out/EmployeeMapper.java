package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.employee.in.EmployeeDtoIn;
import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.exceptions.UserNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.service.in.UserService;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements OutputMapper<EmployeeDto, EmployeeEntity>, InputMapper<EmployeeEntity, EmployeeDtoIn> {

   private final UserService userService;

   public EmployeeMapper(UserService userService) {
      this.userService = userService;
   }

   @Override
   public EmployeeEntity inputToEntity(EmployeeDtoIn dto) {
      UserEntity userEntity = getUser(dto.idUser());
      return EmployeeEntity.builder()
              .user(userEntity)
              .salary(dto.salary())
              .shift(dto.shift())
              .active(true)
              .build();
   }

   @Override
   public EmployeeDto toDto(EmployeeEntity entity) {
      return EmployeeDto.builder()
              .id(entity.getId() != null ? entity.getId(): null)
              .idUser(entity.getUser().getId())
              .name(entity.getFullName())
              .salary(entity.getSalary())
              .shift(entity.getShift())
              .build();
   }

   @Override
   public EmployeeEntity toEntity(EmployeeDto dto) {
      UserEntity userEntity = getUser(dto.idUser());
      return EmployeeEntity.builder()
              .id(dto.id())
              .user(userEntity)
              .salary(dto.salary())
              .shift(dto.shift())
              .active(true)
              .build();
   }

   private UserEntity getUser(Long id){
      return userService.getUserById(id)
              .orElseThrow(() -> new UserNotFoundException("User not found."));
   }
}
