package jame.dev.inventory.restController.priv.admin;

import jame.dev.inventory.dtos.user.in.UserEmpInputInfo;
import jame.dev.inventory.dtos.user.out.UserInfoDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.models.RoleEntity;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.service.in.EmployeeService;
import jame.dev.inventory.service.in.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${app.mapping.admin}")
@PreAuthorize("hasRole('ADMIN')")
public class UserControllerAdmin {

   private final UserService userService;
   private final EmployeeService employeeService;
   private final DtoMapper<UserInfoDto, UserEntity> mapper;

   public UserControllerAdmin(UserService userService, EmployeeService employeeService, DtoMapper<UserInfoDto, UserEntity> mapper) {
      this.userService = userService;
      this.employeeService = employeeService;
      this.mapper = mapper;
   }

   @GetMapping("/users")
   public ResponseEntity<List<UserInfoDto>> getUsers() {
      List<UserInfoDto> userDtoList = userService.getAll()
              .stream()
              .map(mapper::mapToDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(userDtoList);
   }

   @PostMapping("/addUser")
   public ResponseEntity<UserInfoDto> addUser(@RequestBody UserEmpInputInfo userDto) {
      UserEntity userSaved = userService.save(
              UserEntity.builder()
                      .name(userDto.name())
                      .lastName(userDto.lastName())
                      .email(userDto.email())
                      .password(userDto.password())
                      .roles(userDto.role().stream().map(r -> new RoleEntity(null, r)).collect(Collectors.toSet()))
                      .build()
      );

      employeeService.save(EmployeeEntity.builder()
                      .user(userSaved)
                      .jobTitle(userDto.jobTitle())
                      .salary(userDto.salary())
                      .shift(userDto.shift())
                      .build());

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.mapToDto(userSaved));
   }

   @DeleteMapping("/dropUser/{id}")
   public ResponseEntity<Void> dropUser(@PathVariable long id) {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
   }
}
