package jame.dev.inventory.repo;

import jame.dev.inventory.models.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProviderRepository extends JpaRepository<ProviderEntity, Long> {

   Optional<ProviderEntity> findByName(String providerName);
}
