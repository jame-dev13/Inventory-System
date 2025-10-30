package jame.dev.inventory.restController.priv;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.provider.in.ProviderInDto;
import jame.dev.inventory.dtos.provider.out.ProviderDto;
import jame.dev.inventory.exceptions.ProviderProductNotFoundException;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.ProviderEntity;
import jame.dev.inventory.service.in.ProductService;
import jame.dev.inventory.service.in.ProviderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("${app.mapping}/providers")
public class ProviderController {

   private final ProviderService providerService;
   private final ProductService productService;
   private final DtoMapper<ProviderDto, ProviderEntity> providerMapper;
   private final DtoMapper<ProductDto, ProductEntity> productMapper;

   public ProviderController(ProviderService providerService, ProductService productService, DtoMapper<ProviderDto, ProviderEntity> providerMapper, DtoMapper<ProductDto, ProductEntity> productMapper) {
      this.providerService = providerService;
      this.productService = productService;
      this.providerMapper = providerMapper;
      this.productMapper = productMapper;
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<ProviderDto>> getProviders() {
      List<ProviderDto> providerDtoList = providerService.getAll()
              .stream()
              .map(providerMapper::mapToDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping("/{idProvider}/products")
   public ResponseEntity<List<ProductDto>> getProductsByProvider(@PathVariable @Nonnull Long idProvider) {
      Predicate<Long> filter = providerId -> idProvider == providerId.longValue();
      List<ProductEntity> filteredList = productService.getAll()
              .stream()
              .filter(p -> filter.test(p.getProvider().getId()))
              .toList();

      List<ProductDto> productDtosList = filteredList.stream()
              .map(productMapper::mapToDto)
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(productDtosList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PostMapping
   public ResponseEntity<ProviderDto> addProvider(@RequestBody ProviderInDto providerDto) {
      ProviderEntity provider = providerService.save(
              ProviderEntity.builder()
                      .name(providerDto.name())
                      .phone(providerDto.phone())
                      .email(providerDto.email())
                      .build()
      );
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerMapper.mapToDto(provider));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<ProviderDto> patchProvider(@PathVariable @Nonnull Long id, @RequestBody ProviderInDto providerDto) {
      ProviderEntity providerEntity = providerService.getProviderById(id)
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));

      providerEntity.setName(providerDto.name());
      providerEntity.setPhone(providerDto.phone());
      providerEntity.setEmail(providerDto.email());

      ProviderEntity providerPatched = providerService.save(providerEntity);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerMapper.mapToDto(providerPatched));
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropProvider(@PathVariable @Nonnull Long id) {
      providerService.deleteProviderById(id);
      return ResponseEntity.noContent().build();
   }
}

