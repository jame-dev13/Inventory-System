package jame.dev.inventory.repo;

import jame.dev.inventory.models.ProductEntity;

public interface IProductRepository extends CustomJpaRepository<ProductEntity, Long> {
}
