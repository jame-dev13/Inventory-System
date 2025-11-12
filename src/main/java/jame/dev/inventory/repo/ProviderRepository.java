package jame.dev.inventory.repo;

import jame.dev.inventory.models.dao.ProductEntity;
import jame.dev.inventory.models.dao.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {

   Optional<ProviderEntity> findByName(String providerName);

   @Query(value = "SELECT p FROM ProductEntity p JOIN FETCH p.provider WHERE p.provider.id = :providerId")
   List<ProductEntity> getAllProductsByProviderId(@Param("providerId") Long providerId);
}
