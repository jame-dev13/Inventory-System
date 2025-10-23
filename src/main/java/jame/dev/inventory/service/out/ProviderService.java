package jame.dev.inventory.service.out;

import jame.dev.inventory.models.ProviderEntity;
import jame.dev.inventory.repo.IProviderRepository;
import jame.dev.inventory.service.in.IProviderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProviderService implements IProviderService {

   @Autowired
   private final IProviderRepository repo;

   @Override
   public List<ProviderEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<ProviderEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public ProviderEntity save(ProviderEntity provider) {
      return repo.save(provider);
   }

   @Override
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
