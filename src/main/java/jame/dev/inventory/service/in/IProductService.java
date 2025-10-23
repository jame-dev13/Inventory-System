package jame.dev.inventory.service.in;

import jame.dev.inventory.models.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface IProductService {
   List<ProductEntity> getAll();
   Optional<ProductEntity> getUserById(Long id);
   ProductEntity save(ProductEntity product);
   void deleteUserById(Long id);
}
