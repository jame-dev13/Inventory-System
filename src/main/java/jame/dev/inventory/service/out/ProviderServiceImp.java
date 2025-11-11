package jame.dev.inventory.service.out;

import jame.dev.inventory.dtos.provider.in.ProviderInDto;
import jame.dev.inventory.models.dao.ProductEntity;
import jame.dev.inventory.models.dao.ProviderEntity;
import jame.dev.inventory.repo.IProviderRepository;
import jame.dev.inventory.service.in.ProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImp implements ProviderService {

   private final IProviderRepository repo;

   public ProviderServiceImp(IProviderRepository repo) {
      this.repo = repo;
   }

   @Override
   public List<ProviderEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public List<ProductEntity> getProductsByProvider(Long id) {
      return repo.getAllProductsByProviderId(id);
   }

   @Override
   public Optional<ProviderEntity> getProviderById(Long id) {
      return repo.findById(id);
   }

   @Override
   public Optional<ProviderEntity> getProviderByName(String name) {
      return repo.findByName(name);
   }

   @Override
   @Transactional
   public ProviderEntity save(ProviderEntity provider) {
      return repo.save(provider);
   }

   @Override
   @Transactional
   public ProviderEntity update(ProviderEntity provider, ProviderInDto providerDto) {
      provider.setName(providerDto.name());
      provider.setPhone(providerDto.phone());
      provider.setEmail(providerDto.email());
      return repo.save(provider);
   }

   @Override
   @Transactional
   public void deleteProviderById(Long id) {
      repo.deleteById(id);
   }
}
