package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.provider.out.ProviderDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.ProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper implements DtoMapper<ProviderDto, ProviderEntity> {
   @Override
   public ProviderDto mapToDto(ProviderEntity entity) {
      return ProviderDto.builder()
              .id(entity.getId())
              .name(entity.getName())
              .phone(entity.getPhone())
              .email(entity.getEmail())
              .build();
   }
}
