package jame.dev.inventory.service.out;

import jame.dev.inventory.dtos.product.in.ProductDtoIn;
import jame.dev.inventory.exceptions.ProviderProductNotFoundException;
import jame.dev.inventory.models.dao.ProductEntity;
import jame.dev.inventory.models.dao.ProviderEntity;
import jame.dev.inventory.repo.IProductRepository;
import jame.dev.inventory.repo.IProviderRepository;
import jame.dev.inventory.service.in.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
   private final IProductRepository repo;
   private final IProviderRepository repoProvider;

   public ProductServiceImp(IProductRepository repo, IProviderRepository repoProvider) {
      this.repo = repo;
      this.repoProvider = repoProvider;
   }

   @Override
   public List<ProductEntity> getAll() {
      return repo.findAllActives();
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
   public ProductEntity update(ProductEntity product, ProductDtoIn productDtoIn) {
      ProviderEntity providerEntity = repoProvider.findById(productDtoIn.providerId())
              .orElseThrow(() -> new ProviderProductNotFoundException("Provider nor found."));
      product.setDescription(productDtoIn.description());
      product.setStock(productDtoIn.stock());
      product.setProvider(providerEntity);
      product.setUnitPrice(productDtoIn.unitPrice());
      return repo.save(product);
   }

   @Override
   @Transactional
   public void deleteProductById(Long id) {
      repo.softDeleteById(id);
   }
}
