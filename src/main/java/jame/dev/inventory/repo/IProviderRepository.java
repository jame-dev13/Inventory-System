package jame.dev.inventory.repo;

import jame.dev.inventory.models.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProviderRepository extends JpaRepository<ProviderEntity, Long> {
}
