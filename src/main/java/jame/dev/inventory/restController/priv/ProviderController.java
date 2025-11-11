package jame.dev.inventory.restController.priv;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.provider.in.ProviderInDto;
import jame.dev.inventory.dtos.provider.out.ProviderDto;
import jame.dev.inventory.exceptions.ProviderProductNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.dao.ProductEntity;
import jame.dev.inventory.models.dao.ProviderEntity;
import jame.dev.inventory.service.in.ProviderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.PROVIDERS;

@RestController
@RequestMapping("${app.mapping}/providers")
public class ProviderController {

   private final ProviderService providerService;
   private final OutputMapper<ProviderDto, ProviderEntity> providerMapper;
   private final OutputMapper<ProductDto, ProductEntity> productMapper;
   private final InputMapper<ProviderEntity, ProviderInDto> providerInMapper;
   private final Cache<ProviderDto> cache;

   public ProviderController(ProviderService providerService, OutputMapper<ProviderDto, ProviderEntity> providerMapper, OutputMapper<ProductDto, ProductEntity> productMapper, InputMapper<ProviderEntity, ProviderInDto> providerInMapper, Cache<ProviderDto> cache) {
      this.providerService = providerService;
      this.providerMapper = providerMapper;
      this.productMapper = productMapper;
      this.providerInMapper = providerInMapper;
      this.cache = cache;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<ProviderDto>> getProviders() {
      Optional<List<ProviderDto>> optionalList = cache.getCache(PROVIDERS.getName());
      if(optionalList.isPresent()){
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(optionalList.get());
      }
      List<ProviderDto> providerDtoList = providerService.getAll()
              .stream()
              .map(providerMapper::toDto)
              .toList();
      cache.saveCache(PROVIDERS.getName(), providerDtoList);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping("/{id}")
   public ResponseEntity<ProviderDto> getProvidersById(@PathVariable Long id) {
      ProviderEntity provider = providerService.getProviderById(id)
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerMapper.toDto(provider));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping("/{idProvider}/products")
   public ResponseEntity<List<ProductDto>> getProductsByProvider(@PathVariable Long idProvider) {
      List<ProductEntity> productEntityList = providerService.getProductsByProvider(idProvider);
      List<ProductDto> productDtoList = productEntityList.stream()
              .map(productMapper::toDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(productDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PostMapping
   public ResponseEntity<ProviderDto> addProvider(@RequestBody ProviderInDto providerDto) {
      ProviderEntity provider = providerService.save(providerInMapper.inputToEntity(providerDto));
      ProviderDto dtoResponse = providerMapper.toDto(provider);
      cache.addData(PROVIDERS.getName(), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<ProviderDto> patchProvider(@PathVariable @Nonnull Long id, @RequestBody ProviderInDto providerDto) {
      ProviderEntity providerEntity = providerService.getProviderById(id)
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));
      ProviderEntity providerPatched = providerService.update(providerEntity, providerDto);
      ProviderDto dtoResponse = providerMapper.toDto(providerPatched);
      cache.updateData(PROVIDERS.getName(), p -> p.id().equals(id), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropProvider(@PathVariable @Nonnull Long id) {
      ProviderEntity providerEntity = providerService.getProviderById(id)
                      .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));
      ProviderDto providerDto = providerMapper.toDto(providerEntity);
      cache.removeData(PROVIDERS.getName(), p -> p.id().equals(id), providerDto);
      providerService.deleteProviderById(id);
      return ResponseEntity.noContent().build();
   }
}

