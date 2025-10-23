package jame.dev.inventory.repo;

import jame.dev.inventory.models.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}
