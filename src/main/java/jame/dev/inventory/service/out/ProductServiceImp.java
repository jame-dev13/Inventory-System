package jame.dev.inventory.service.out;

import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.repo.IProductRepository;
import jame.dev.inventory.service.in.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
   private final IProductRepository repo;

   public ProductServiceImp(IProductRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<ProductEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<ProductEntity> getProductById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public ProductEntity save(ProductEntity product) {
      return repo.save(product);
   }

   @Override
   @Transactional
   public void deleteProductById(Long id) {
      repo.deleteById(id);
   }
}
