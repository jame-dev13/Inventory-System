package jame.dev.inventory.service.in;

import jame.dev.inventory.dtos.user.in.UserDtoIn;
import jame.dev.inventory.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
   List<UserEntity> getAll();
   Optional<UserEntity> getUserById(Long id);
   Optional<UserEntity> getUserByEmail(String email);
   UserEntity save(UserEntity user);
   UserEntity update(Long id, UserDtoIn userDtoIn);
   void deleteUserById(Long id);
}
