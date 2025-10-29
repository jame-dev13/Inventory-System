package jame.dev.inventory.service.in;

import jame.dev.inventory.models.ProviderEntity;

import java.util.List;
import java.util.Optional;

public interface ProviderService {
   List<ProviderEntity> getAll();
   Optional<ProviderEntity> getProviderById(Long id);
   Optional<ProviderEntity> getProviderByName(String name);
   ProviderEntity save(ProviderEntity provider);
   void deleteProviderById(Long id);
}
