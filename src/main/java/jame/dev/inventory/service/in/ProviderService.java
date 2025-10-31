package jame.dev.inventory.service.in;

import jame.dev.inventory.dtos.provider.in.ProviderInDto;
import jame.dev.inventory.models.ProviderEntity;

import java.util.List;
import java.util.Optional;

public interface ProviderService {
   List<ProviderEntity> getAll();
   Optional<ProviderEntity> getProviderById(Long id);
   Optional<ProviderEntity> getProviderByName(String name);
   ProviderEntity save(ProviderEntity provider);
   ProviderEntity update(ProviderEntity provider, ProviderInDto providerDto);
   void deleteProviderById(Long id);
}
