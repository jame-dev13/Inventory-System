package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<ProductDto, ProductEntity> {
   @Override
   public ProductDto mapToDto(ProductEntity entity) {
      return ProductDto.builder()
              .id(entity.getId())
              .description(entity.getDescription())
              .stock(entity.getStock())
              .idProvider(entity.getProvider().getId())
              .unitPrice(entity.getUnitPrice())
              .build();
   }
}
