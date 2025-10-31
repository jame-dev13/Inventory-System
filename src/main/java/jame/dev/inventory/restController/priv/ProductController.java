package jame.dev.inventory.restController.priv;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.product.in.ProductDtoIn;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.exceptions.ProductNotFoundException;
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

@RestController
@RequestMapping("${app.mapping}/products")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class ProductController {

   private final ProductService productService;
   private final ProviderService providerService;
   private final DtoMapper<ProductDto, ProductEntity> mapper;

   public ProductController(ProductService productService, ProviderService providerService, DtoMapper<ProductDto, ProductEntity> mapper) {
      this.productService = productService;
      this.providerService = providerService;
      this.mapper = mapper;
   }

   @GetMapping
   public ResponseEntity<List<ProductDto>> getProducts() {
      List<ProductDto> productsDtoList = productService.getAll()
              .stream()
              .map(mapper::mapToDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(productsDtoList);
   }

   @PostMapping
   public ResponseEntity<ProductDto> addProduct(@RequestBody @Nonnull ProductDtoIn productDto) {
      ProviderEntity provider = providerService.getProviderById(productDto.providerId())
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider not found."));
      ProductEntity productEntity = productService.save(
              ProductEntity.builder()
                      .description(productDto.description())
                      .stock(productDto.stock())
                      .provider(provider)
                      .unitPrice(productDto.unitPrice())
                      .build()
      );
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.mapToDto(productEntity));
   }

   @PatchMapping("/{id}")
   public ResponseEntity<ProductDto> patchProduct(@PathVariable @Nonnull Long id, @RequestBody @Nonnull ProductDtoIn productDto) {
      ProductEntity productEntity = productService.getProductById(id)
              .orElseThrow(() -> new ProductNotFoundException("Product not found."));

      ProductEntity productPatched = productService.update(productEntity, productDto);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.mapToDto(productPatched));

   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropProduct(@PathVariable @Nonnull Long id) {
      productService.deleteProductById(id);
      return ResponseEntity.noContent().build();
   }
}
