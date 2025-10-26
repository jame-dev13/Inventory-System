package jame.dev.inventory.service.in;

import jame.dev.inventory.models.ProviderEntity;

import java.util.List;
import java.util.Optional;

public interface ProviderService {
   List<ProviderEntity> getAll();
   Optional<ProviderEntity> getUserById(Long id);
   ProviderEntity save(ProviderEntity provider);
   void deleteUserById(Long id);
}
