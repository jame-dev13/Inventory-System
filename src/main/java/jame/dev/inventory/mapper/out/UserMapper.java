package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.user.in.UserDtoIn;
import jame.dev.inventory.dtos.user.out.UserDto;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.RoleEntity;
import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.models.enums.ERole;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper implements OutputMapper<UserDto, UserEntity>, InputMapper<UserEntity, UserDtoIn> {


   @Override
   public UserEntity inputToEntity(UserDtoIn dto) {
      return UserEntity.builder()
              .name(dto.name())
              .lastName(dto.lastName())
              .email(dto.email())
              .password(dto.password())
              .roles(mapRoles(dto.role()))
              .active(true)
              .build();
   }

   @Override
   public UserDto toDto(UserEntity entity) {
      return UserDto.builder()
              .id(entity.getId())
              .name(entity.getName())
              .lastName(entity.getLastName())
              .password(entity.getPassword())
              .email(entity.getEmail())
              .role(mapRolesInverse(entity.getRoles()))
              .build();
   }

   @Override
   public UserEntity toEntity(UserDto dto) {
      return UserEntity.builder()
              .id(dto.id())
              .name(dto.name())
              .lastName(dto.lastName())
              .email(dto.email())
              .password(dto.password())
              .roles(mapRoles(dto.role()))
              .active(true)
              .build();
   }

   private Set<RoleEntity> mapRoles(Set<ERole> roles){
      return roles.stream()
              .map(r -> RoleEntity.builder().role(r).build())
              .collect(Collectors.toSet());
   }

   private Set<ERole> mapRolesInverse(Set<RoleEntity> roles){
      return roles.stream()
              .map(RoleEntity::getRole)
              .collect(Collectors.toSet());
   }
}
