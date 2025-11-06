package jame.dev.inventory.restController.priv;

import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.dtos.user.in.UserDtoIn;
import jame.dev.inventory.dtos.user.out.UserDto;
import jame.dev.inventory.exceptions.UserNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.service.in.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.USERS;

@RestController
@RequestMapping("${app.mapping}/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

   private final UserService userService;
   private final OutputMapper<UserDto, UserEntity> mapperOut;
   private final InputMapper<UserEntity, UserDtoIn> mapperIn;
   private final Cache<UserDto> cache;

   public UserController(UserService userService, OutputMapper<UserDto, UserEntity> mapperOut, InputMapper<UserEntity, UserDtoIn> mapperIn, Cache<UserDto> cache) {
      this.userService = userService;
      this.mapperOut = mapperOut;
      this.mapperIn = mapperIn;
      this.cache = cache;
   }

   @GetMapping
   public ResponseEntity<List<UserDto>> getUsers(){
      Optional<List<UserDto>> cacheList = cache.getCache(USERS.getName());
      if(cacheList.isPresent()){
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(cacheList.get());
      }

      List<UserDto> dtoResponse = userService.getAll()
              .stream()
              .map(mapperOut::toDto)
              .toList();
      cache.saveCache(USERS.getName(), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }

   @GetMapping("/{id}")
   public ResponseEntity<UserDto> getUser(@PathVariable Long id){
      UserEntity userEntity = userService.getUserById(id)
              .orElseThrow(() -> new UserNotFoundException("User not found."));
      UserDto dtoResponse = mapperOut.toDto(userEntity);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }

   @PostMapping
   public ResponseEntity<UserDto> postUser(@RequestBody UserDtoIn userDtoIn){
      UserEntity userEntity = userService.save(mapperIn.inputToEntity(userDtoIn));
      UserDto dtoResponse = mapperOut.toDto(userEntity);
      cache.addData(USERS.getName(), dtoResponse);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }

   @PatchMapping("/{id}")
   public ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody UserDtoIn userDtoIn){
      UserEntity userPatched = userService.update(id, userDtoIn);
      UserDto userDto = mapperOut.toDto(userPatched);
      cache.updateData(USERS.getName(), u -> u.id().equals(id), userDto);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(userDto);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<UserDto> deleteUser(@PathVariable Long id){
      UserEntity userEntity = userService.getUserById(id)
              .orElseThrow(() -> new UserNotFoundException("User not found."));
      UserDto userDto = mapperOut.toDto(userEntity);
      cache.removeData(USERS.getName(), u -> u.id().equals(id), userDto);
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
   }
}
