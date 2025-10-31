package jame.dev.inventory.service.in;

import jame.dev.inventory.dtos.product.in.ProductDtoIn;
import jame.dev.inventory.models.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
   List<ProductEntity> getAll();
   Optional<ProductEntity> getProductById(Long id);
   ProductEntity save(ProductEntity product);
   ProductEntity update(ProductEntity product, ProductDtoIn productDtoIn);
   void deleteProductById(Long id);
}
