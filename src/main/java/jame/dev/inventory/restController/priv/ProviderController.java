package jame.dev.inventory.restController.priv;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.provider.in.ProviderInDto;
import jame.dev.inventory.dtos.provider.out.ProviderDto;
import jame.dev.inventory.exceptions.ProviderProductNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
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
   private final OutputMapper<ProviderDto, ProviderEntity> providerMapper;
   private final OutputMapper<ProductDto, ProductEntity> productMapper;
   private final InputMapper<ProviderEntity, ProviderInDto> providerInMapper;

   public ProviderController(ProviderService providerService, ProductService productService, OutputMapper<ProviderDto, ProviderEntity> providerMapper, OutputMapper<ProductDto, ProductEntity> productMapper, InputMapper<ProviderEntity, ProviderInDto> providerInMapper) {
      this.providerService = providerService;
      this.productService = productService;
      this.providerMapper = providerMapper;
      this.productMapper = productMapper;
      this.providerInMapper = providerInMapper;
   }


   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping
   public ResponseEntity<List<ProviderDto>> getProviders() {
      List<ProviderDto> providerDtoList = providerService.getAll()
              .stream()
              .map(providerMapper::toDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerDtoList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @GetMapping("/{idProvider}/products")
   public ResponseEntity<List<ProductDto>> getProductsByProvider(@PathVariable Long idProvider) {
      Predicate<ProductEntity> filterById = p -> p.getProvider() != null && p.getProvider().getId().equals(idProvider);
      List<ProductDto> filteredList = productService.getAll()
              .stream()
              .filter(filterById)
              .map(productMapper::toDto)
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(filteredList);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PostMapping
   public ResponseEntity<ProviderDto> addProvider(@RequestBody ProviderInDto providerDto) {
      ProviderEntity provider = providerService.save(providerInMapper.inputToEntity(providerDto));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerMapper.toDto(provider));
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
   @PatchMapping("/{id}")
   public ResponseEntity<ProviderDto> patchProvider(@PathVariable @Nonnull Long id, @RequestBody ProviderInDto providerDto) {
      ProviderEntity providerEntity = providerService.getProviderById(id)
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));
      ProviderEntity providerPatched = providerService.update(providerEntity, providerDto);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerMapper.toDto(providerPatched));
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropProvider(@PathVariable @Nonnull Long id) {
      providerService.deleteProviderById(id);
      return ResponseEntity.noContent().build();
   }
}

