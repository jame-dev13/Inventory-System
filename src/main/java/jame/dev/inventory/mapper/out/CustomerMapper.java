package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.customer.in.CustomerDtoIn;
import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements OutputMapper<CustomerDto, CustomerEntity>,
        InputMapper<CustomerEntity, CustomerDtoIn> {

   @Override
   public CustomerDto toDto(CustomerEntity entity) {
      return  CustomerDto.builder()
              .id(entity.getId() != null ? entity.getId() : null)
              .name(entity.getFullName())
              .email(entity.getEmail())
              .phone(entity.getPhone())
              .age(entity.getAge())
              .build();
   }

   @Override
   public CustomerEntity toEntity(CustomerDto dto) {
      String[] splitNames = splitFullName(dto.name());
      return CustomerEntity.builder()
              .id(dto.id())
              .name(splitNames[0])
              .lastName(splitNames[1])
              .email(dto.email())
              .phone(dto.phone())
              .age(dto.age())
              .build();
   }

   @Override
   public CustomerEntity inputToEntity(CustomerDtoIn dto) {
      return  CustomerEntity.builder()
              .name(dto.name())
              .lastName(dto.lastName())
              .email(dto.email())
              .phone(dto.phone())
              .age(dto.age())
              .build();
   }

   private String[] splitFullName(String fullName){
      return fullName.split(" ");
   }
}
