package jame.dev.inventory.service.out;

import jame.dev.inventory.models.ProviderEntity;
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
   public Optional<ProviderEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   @Transactional
   public ProviderEntity save(ProviderEntity provider) {
      return repo.save(provider);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
