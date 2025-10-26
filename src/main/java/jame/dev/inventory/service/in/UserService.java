package jame.dev.inventory.service.in;

import jame.dev.inventory.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
   List<UserEntity> getAll();
   Optional<UserEntity> getUserById(Long id);
   Optional<UserEntity> getUserByEmail(String email);
   UserEntity save(UserEntity user);
   void deleteUserById(Long id);
}
