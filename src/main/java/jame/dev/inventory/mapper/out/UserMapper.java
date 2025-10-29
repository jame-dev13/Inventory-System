package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.user.out.UserInfoDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.RoleEntity;
import jame.dev.inventory.models.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper implements DtoMapper<UserInfoDto, UserEntity> {
   @Override
   public UserInfoDto mapToDto(UserEntity entity) {
      return UserInfoDto.builder()
              .id(entity.getId())
              .name(entity.getName())
              .lastName(entity.getLastName())
              .email(entity.getEmail())
              .role(entity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toSet()))
              .build();
   }
}
