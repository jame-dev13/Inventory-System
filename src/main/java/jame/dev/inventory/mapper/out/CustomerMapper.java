package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements DtoMapper<CustomerDto, CustomerEntity> {
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
}
