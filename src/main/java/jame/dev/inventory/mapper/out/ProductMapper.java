package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.exceptions.ProviderProductNotFoundException;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.ProviderEntity;
import jame.dev.inventory.service.in.ProviderService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<ProductDto, ProductEntity>, EntityMapper<ProductEntity, ProductDto> {
   private final ProviderService providerService;

   public ProductMapper(ProviderService providerService) {
      this.providerService = providerService;
   }

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

   @Override
   public ProductEntity mapToEntity(ProductDto dto) {
      ProviderEntity providerEntity = providerService.getProviderById(dto.idProvider())
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));
      return ProductEntity.builder()
              .id(dto.id())
              .description(dto.description())
              .stock(dto.stock())
              .provider(providerEntity)
              .unitPrice(dto.unitPrice())
              .build();
   }
}
