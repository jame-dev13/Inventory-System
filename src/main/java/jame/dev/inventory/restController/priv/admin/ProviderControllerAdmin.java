package jame.dev.inventory.restController.priv.admin;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.provider.in.ProviderInDto;
import jame.dev.inventory.dtos.provider.out.ProviderDto;
import jame.dev.inventory.exceptions.ProviderProductNotFoundException;
import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.ProviderEntity;
import jame.dev.inventory.service.in.ProductService;
import jame.dev.inventory.service.in.ProviderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("${app.mapping.admin}")
@PreAuthorize("hasRole('ADMIN')")
public class ProviderControllerAdmin {

   private final ProviderService providerService;
   private final ProductService productService;

   public ProviderControllerAdmin(ProviderService providerService, ProductService productService) {
      this.providerService = providerService;
      this.productService = productService;
   }

   @GetMapping("/providers")
   public ResponseEntity<List<ProviderDto>> getProviders() {
      List<ProviderDto> providerDtoList = providerService.getAll()
              .stream()
              .map(p -> ProviderDto.builder()
                      .id(p.getId())
                      .name(p.getName())
                      .phone(p.getPhone())
                      .email(p.getEmail())
                      .build())
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerDtoList);
   }

   @GetMapping("/providers/{idProvider}/products")

   public ResponseEntity<List<ProductDto>> getProductsByProvider(@PathVariable @Nonnull Long idProvider) {
      Predicate<Long> filter = providerId -> idProvider == providerId.longValue();
      List<ProductEntity> filteredList = productService.getAll()
              .stream()
              .filter(p -> filter.test(p.getProvider().getId()))
              .toList();
      List<ProductDto> productDtosList = filteredList.stream()
              .map(p -> ProductDto.builder()
                      .id(p.getId())
                      .description(p.getDescription())
                      .stock(p.getStock())
                      .idProvider(p.getProvider().getId())
                      .unitPrice(p.getUnitPrice())
                      .build())
              .toList();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(productDtosList);
   }

   @PostMapping("/addProvider")
   public ResponseEntity<ProviderDto> addProvider(@RequestBody ProviderInDto providerDto) {
      ProviderEntity provider = providerService.save(
              ProviderEntity.builder()
                      .name(providerDto.name())
                      .phone(providerDto.phone())
                      .email(providerDto.email())
                      .build()
      );
      ProviderDto providerDtoResponse = ProviderDto.builder()
              .id(provider.getId())
              .name(provider.getName())
              .phone(provider.getPhone())
              .email(provider.getEmail())
              .build();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerDtoResponse);
   }

   @PatchMapping("/patchProvider/{id}")
   public ResponseEntity<ProviderDto> patchProvider(@PathVariable @Nonnull Long id, @RequestBody ProviderInDto providerDto) {
      ProviderEntity providerEntity = providerService.getProviderById(id)
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));

      providerEntity.setName(providerDto.name());
      providerEntity.setPhone(providerDto.phone());
      providerEntity.setEmail(providerDto.email());

      ProviderEntity providerPatched = providerService.save(providerEntity);
      ProviderDto providerDtoResponse = ProviderDto.builder()
              .id(providerPatched.getId())
              .name(providerPatched.getName())
              .phone(providerPatched.getPhone())
              .email(providerPatched.getEmail())
              .build();

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(providerDtoResponse);
   }

   @DeleteMapping("/dropProvider/{id}")
   public ResponseEntity<Void> dropProvider(@PathVariable @Nonnull Long id) {
      providerService.deleteProviderById(id);
      return ResponseEntity.noContent().build();
   }
}

