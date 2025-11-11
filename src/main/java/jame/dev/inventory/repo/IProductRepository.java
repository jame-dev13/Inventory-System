package jame.dev.inventory.repo;

import jame.dev.inventory.models.dao.ProductEntity;

public interface IProductRepository extends CustomJpaRepository<ProductEntity, Long> {
}
