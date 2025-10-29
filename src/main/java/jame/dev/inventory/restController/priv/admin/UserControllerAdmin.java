package jame.dev.inventory.restController.priv.admin;

import jame.dev.inventory.dtos.user.in.UserEmpInputInfo;
import jame.dev.inventory.dtos.user.out.UserInfoDto;
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

   public UserControllerAdmin(UserService userService, EmployeeService employeeService) {
      this.userService = userService;
      this.employeeService = employeeService;
   }

   @GetMapping("/users")
   public ResponseEntity<List<UserInfoDto>> getUsers(){
      List<UserInfoDto> userDto = userService.getAll()
              .stream()
              .map(u -> UserInfoDto.builder()
                      .id(u.getId())
                      .name(u.getName())
                      .lastName(u.getLastName())
                      .email(u.getEmail())
                      .role(u.getRoles().stream().map(RoleEntity::getRole)
                              .collect(Collectors.toSet()))
              .build())
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(userDto);
   }

   @PostMapping("/addUser")
   public ResponseEntity<UserInfoDto> addUser(@RequestBody UserEmpInputInfo userDto) {
      UserEntity userEntity = UserEntity.builder()
              .id(null)
              .name(userDto.name())
              .lastName(userDto.lastName())
              .email(userDto.email())
              .password(userDto.password())
              .roles(userDto.role().stream().map(r -> new RoleEntity(null, r)).collect(Collectors.toSet()))
              .build();
      var user = userService.save(userEntity);

      EmployeeEntity employeeEntity = EmployeeEntity.builder()
              .user(user)
              .jobTitle(userDto.jobTitle())
              .salary(userDto.salary())
              .shift(userDto.shift())
              .build();
      employeeService.save(employeeEntity);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(UserInfoDto.builder()
                      .id(user.getId())
                      .name(user.getName())
                      .lastName(userDto.lastName())
                      .email(user.getEmail())
                      .role(user.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toSet()))
                      .build());
   }

   @DeleteMapping("/dropUser/{id}")
   public ResponseEntity<Void> dropUser(@PathVariable long id){
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
   }
}
