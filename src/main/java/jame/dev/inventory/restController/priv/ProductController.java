package jame.dev.inventory.restController.priv;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.dtos.product.in.ProductDtoIn;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.exceptions.ProductNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.ProductEntity;
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
   private final OutputMapper<ProductDto, ProductEntity> mapperOut;
   private final InputMapper<ProductEntity, ProductDtoIn> mapperIn;

   public ProductController(ProductService productService, ProviderService providerService, OutputMapper<ProductDto, ProductEntity> mapperOut, InputMapper<ProductEntity, ProductDtoIn> mapperIn) {
      this.productService = productService;
      this.providerService = providerService;
      this.mapperOut = mapperOut;
      this.mapperIn = mapperIn;
   }

   @GetMapping
   public ResponseEntity<List<ProductDto>> getProducts() {
      List<ProductDto> productsDtoList = productService.getAll()
              .stream()
              .map(mapperOut::toDto)
              .toList();
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(productsDtoList);
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
      ProductEntity productEntity = productService.getProductById(id)
              .orElseThrow(() -> new ProductNotFoundException("Product not found."));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(productEntity));
   }

   @PostMapping
   public ResponseEntity<ProductDto> addProduct(@RequestBody @Nonnull ProductDtoIn productDto) {
      ProductEntity productEntity = productService.save(mapperIn.inputToEntity(productDto));
      System.out.println(productEntity);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(productEntity));
   }

   @PatchMapping("/{id}")
   public ResponseEntity<ProductDto> patchProduct(@PathVariable @Nonnull Long id, @RequestBody @Nonnull ProductDtoIn productDto) {
      ProductEntity productEntity = productService.getProductById(id)
              .orElseThrow(() -> new ProductNotFoundException("Product not found."));

      ProductEntity productPatched = productService.update(productEntity, productDto);

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(productPatched));

   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropProduct(@PathVariable @Nonnull Long id) {
      productService.deleteProductById(id);
      return ResponseEntity.noContent().build();
   }
}
