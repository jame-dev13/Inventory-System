package jame.dev.inventory.service.out;

import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.repo.IProductRepository;
import jame.dev.inventory.service.in.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
   @Autowired
   private final IProductRepository repo;

   @Override
   public List<ProductEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<ProductEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public ProductEntity save(ProductEntity product) {
      return repo.save(product);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
