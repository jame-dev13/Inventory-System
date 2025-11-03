package jame.dev.inventory.restController.priv;

import jame.dev.inventory.dtos.user.in.UserEmpInputInfo;
import jame.dev.inventory.dtos.user.out.UserInfoDto;
import jame.dev.inventory.exceptions.UserNotFoundException;
import jame.dev.inventory.mapper.in.OutputMapper;
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
@RequestMapping("${app.mapping}/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

   private final UserService userService;
   private final EmployeeService employeeService;
   private final OutputMapper<UserInfoDto, UserEntity> mapper;

   public UserController(UserService userService, EmployeeService employeeService, OutputMapper<UserInfoDto, UserEntity> mapper) {
      this.userService = userService;
      this.employeeService = employeeService;
      this.mapper = mapper;
   }

   @GetMapping
   public ResponseEntity<List<UserInfoDto>> getUsers() {
      List<UserInfoDto> userDtoList = userService.getAll()
              .stream()
              .map(mapper::toDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(userDtoList);
   }

   @GetMapping("/{id}")
   public ResponseEntity<UserInfoDto> getUserById(@PathVariable Long id) {
      UserEntity userEntity = userService.getUserById(id)
              .orElseThrow(() -> new UserNotFoundException("User not found."));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.toDto(userEntity));
   }

   @PostMapping
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
                      .salary(userDto.salary())
                      .shift(userDto.shift())
                      .build());

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.toDto(userSaved));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropUser(@PathVariable long id) {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
   }
}
