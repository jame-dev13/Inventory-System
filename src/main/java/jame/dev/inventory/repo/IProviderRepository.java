package jame.dev.inventory.repo;

import jame.dev.inventory.models.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IProviderRepository extends JpaRepository<ProviderEntity, Long> {

   Optional<ProviderEntity> findByName(String providerName);

   @Modifying
   @Transactional
   @Query(value = "UPDATE products SET id_provider = NULL WHERE id_provider = :id", nativeQuery = true)
   void setRelationsToNullOnDelete(Long id);
}
