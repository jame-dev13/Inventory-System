package jame.dev.inventory.repo;

import jame.dev.inventory.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
}
