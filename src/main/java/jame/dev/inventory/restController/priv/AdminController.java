package jame.dev.inventory.restController.priv;

import jame.dev.inventory.dtos.user.out.UserInfoDto;
import jame.dev.inventory.models.RoleEntity;
import jame.dev.inventory.service.in.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${app.mapping.admin}")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

   private final UserService userService;

   public AdminController(UserService userService) {
      this.userService = userService;
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
}
