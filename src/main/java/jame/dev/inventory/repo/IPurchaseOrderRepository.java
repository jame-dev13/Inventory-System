package jame.dev.inventory.repo;

import jame.dev.inventory.models.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Long> {
}
