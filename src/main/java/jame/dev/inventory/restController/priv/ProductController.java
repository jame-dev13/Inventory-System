package jame.dev.inventory.restController.priv;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.dtos.product.in.ProductDtoIn;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.exceptions.ProductNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.dao.ProductEntity;
import jame.dev.inventory.service.in.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.PRODUCTS;

@RestController
@RequestMapping("${app.mapping}/products")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class ProductController {

   private final ProductService productService;
   private final OutputMapper<ProductDto, ProductEntity> mapperOut;
   private final InputMapper<ProductEntity, ProductDtoIn> mapperIn;
   private final Cache<ProductDto> cache;

   public ProductController(ProductService productService, OutputMapper<ProductDto, ProductEntity> mapperOut, InputMapper<ProductEntity, ProductDtoIn> mapperIn, Cache<ProductDto> cache) {
      this.productService = productService;
      this.mapperOut = mapperOut;
      this.mapperIn = mapperIn;
      this.cache = cache;
   }

   @GetMapping
   public ResponseEntity<List<ProductDto>> getProducts() {
      Optional<List<ProductDto>> optionalList = cache.getCache(PRODUCTS.getName());
      if (optionalList.isPresent()) {
         System.out.println("Products get from the cache.");
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(optionalList.get());
      }
      List<ProductDto> productsDtoList = productService.getAll()
              .stream()
              .map(mapperOut::toDto)
              .toList();
      cache.saveCache(PRODUCTS.getName(), productsDtoList);
      System.out.println("Products saved in cache.");
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(productsDtoList);
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
      ProductEntity productEntity = productService.getProductById(id)
              .orElseThrow(() -> new ProductNotFoundException("Product not found."));
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapperOut.toDto(productEntity));
   }

   @PostMapping
   public ResponseEntity<ProductDto> addProduct(@RequestBody @Nonnull ProductDtoIn productDto) {
      ProductEntity productEntity = productService.save(mapperIn.inputToEntity(productDto));
      ProductDto dtoResponse = mapperOut.toDto(productEntity);
      cache.addData(PRODUCTS.getName(), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);
   }

   @PatchMapping("/{id}")
   public ResponseEntity<ProductDto> patchProduct(@PathVariable @Nonnull Long id, @RequestBody @Nonnull ProductDtoIn productDto) {
      ProductEntity productEntity = productService.getProductById(id)
              .orElseThrow(() -> new ProductNotFoundException("Product not found."));

      ProductEntity productPatched = productService.update(productEntity, productDto);

      ProductDto dtoResponse = mapperOut.toDto(productPatched);
      cache.updateData(PRODUCTS.getName(), p -> p.id().equals(id), dtoResponse);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoResponse);

   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> dropProduct(@PathVariable @Nonnull Long id) {
      ProductEntity productEntity = productService.getProductById(id)
              .orElseThrow(() -> new ProductNotFoundException("Product not found."));
      cache.removeData(PRODUCTS.getName(), p -> p.id().equals(id), mapperOut.toDto(productEntity));
      productService.deleteProductById(id);
      return ResponseEntity.noContent().build();
   }
}
