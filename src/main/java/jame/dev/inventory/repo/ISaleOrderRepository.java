package jame.dev.inventory.repo;

import jame.dev.inventory.models.SaleOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleOrderRepository extends JpaRepository<SaleOrderEntity, Long> {
}
