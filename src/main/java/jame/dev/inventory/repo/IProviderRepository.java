package jame.dev.inventory.repo;

import jame.dev.inventory.models.ProductEntity;
import jame.dev.inventory.models.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IProviderRepository extends JpaRepository<ProviderEntity, Long> {

   Optional<ProviderEntity> findByName(String providerName);

   @Modifying
   @Transactional
   @Query(value = "UPDATE products SET id_provider = NULL WHERE id_provider = :id", nativeQuery = true)
   void setRelationsToNullOnDelete(Long id);

   @Query(value = "SELECT p FROM ProductEntity p JOIN FETCH p.provider WHERE p.provider.id = :providerId")
   List<ProductEntity> getAllProductsByProviderId(@Param("providerId") Long providerId);
}
