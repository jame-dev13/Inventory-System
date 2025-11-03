package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.provider.in.ProviderInDto;
import jame.dev.inventory.dtos.provider.out.ProviderDto;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.ProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper implements OutputMapper<ProviderDto, ProviderEntity>, InputMapper<ProviderEntity, ProviderInDto> {

   @Override
   public ProviderEntity inputToEntity(ProviderInDto dto) {
      return ProviderEntity.builder()
              .name(dto.name())
              .email(dto.email())
              .phone(dto.phone())
              .build();
   }

   @Override
   public ProviderDto toDto(ProviderEntity entity) {
      return ProviderDto.builder()
              .id(entity.getId())
              .name(entity.getName())
              .phone(entity.getPhone())
              .email(entity.getEmail())
              .build();
   }

   @Override
   public ProviderEntity toEntity(ProviderDto dto) {
      return ProviderEntity.builder()
              .id(dto.id())
              .name(dto.name())
              .email(dto.email())
              .phone(dto.phone())
              .build();
   }
}
