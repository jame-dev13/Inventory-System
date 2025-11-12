package jame.dev.inventory.service.out;

import jame.dev.inventory.dtos.user.in.UserDtoIn;
import jame.dev.inventory.exceptions.UserNotFoundException;
import jame.dev.inventory.models.dao.RoleEntity;
import jame.dev.inventory.models.dao.UserEntity;
import jame.dev.inventory.models.enums.Role;
import jame.dev.inventory.repo.UserRepository;
import jame.dev.inventory.service.in.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

   private final UserRepository repo;

   public UserServiceImp(UserRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<UserEntity> getAll() {
      return repo.findAllActives();
   }

   @Override
   public Optional<UserEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public Optional<UserEntity> getUserByEmail(String email) {
      return repo.findByEmail(email);
   }

   @Override
   @Transactional
   public UserEntity save(UserEntity user) {
      return repo.save(user);
   }

   @Override
   @Transactional
   public UserEntity update(Long id, UserDtoIn userDtoIn) {
      UserEntity userEntity = repo.findById(id)
              .orElseThrow(() -> new UserNotFoundException("User not found."));
      userEntity.setName(userDtoIn.name());
      userEntity.setLastName(userDtoIn.lastName());
      userEntity.setEmail(userDtoIn.email());
      userEntity.setPassword(userDtoIn.password());
      userEntity.setRoles(mapRoles(userDtoIn.role()));
      return repo.save(userEntity);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      repo.softDeleteById(id);
   }

   private Set<RoleEntity> mapRoles(Set<Role> roles){
      return roles.stream()
              .map(r -> RoleEntity.builder().role(r).build())
              .collect(Collectors.toSet());
   }
}
