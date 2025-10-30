package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements DtoMapper<CustomerDto, CustomerEntity>, EntityMapper<CustomerEntity, CustomerDto> {
   @Override
   public CustomerDto mapToDto(CustomerEntity entity) {
      return  CustomerDto.builder()
              .id(entity.getId())
              .name(entity.getFullName())
              .email(entity.getEmail())
              .phone(entity.getPhone())
              .age(entity.getAge())
              .build();
   }

   @Override
   public CustomerEntity mapToEntity(CustomerDto dto) {
      String[] splitFullName = dto.name().split(" ");
      return CustomerEntity.builder()
              .id(dto.id())
              .name(splitFullName[0])
              .lastName(splitFullName[1])
              .email(dto.email())
              .phone(dto.phone())
              .age(dto.age())
              .build();
   }
}
