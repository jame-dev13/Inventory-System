package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.product.in.ProductDtoIn;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.exceptions.ProviderProductNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.ProviderEntity;
import jame.dev.inventory.service.in.ProviderService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements OutputMapper<ProductDto, ProductEntity>, InputMapper<ProductEntity, ProductDtoIn> {
   private final ProviderService providerService;

   public ProductMapper(ProviderService providerService) {
      this.providerService = providerService;
   }

   @Override
   public ProductEntity inputToEntity(ProductDtoIn dto) {
      return ProductEntity.builder()
              .description(dto.description())
              .stock(dto.stock())
              .provider(getProvider(dto.providerId()))
              .unitPrice(dto.unitPrice())
              .build();
   }

   @Override
   public ProductDto toDto(ProductEntity entity) {
      Long idProvider = entity.getProvider() != null ? entity.getProvider().getId() : null;
      return ProductDto.builder()
              .id(entity.getId() != null ? entity.getId() : null)
              .description(entity.getDescription())
              .stock(entity.getStock())
              .idProvider(idProvider)
              .unitPrice(entity.getUnitPrice())
              .build();
   }

   @Override
   public ProductEntity toEntity(ProductDto dto) {
      return ProductEntity.builder()
              .id(dto.id())
              .description(dto.description())
              .stock(dto.stock())
              .provider(getProvider(dto.idProvider()))
              .unitPrice(dto.unitPrice())
              .active(true)
              .build();
   }

   private ProviderEntity getProvider(Long id){
      return providerService.getProviderById(id)
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));
   }
}
