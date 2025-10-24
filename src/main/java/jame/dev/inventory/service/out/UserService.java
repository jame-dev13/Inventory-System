package jame.dev.inventory.service.out;

import jame.dev.inventory.models.UserEntity;
import jame.dev.inventory.repo.IUserRepository;
import jame.dev.inventory.service.in.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
   @Autowired
   private final IUserRepository repo;

   @Override
   public List<UserEntity> getAll() {
      return repo.findAll();
   }

   @Override
   public Optional<UserEntity> getUserById(Long id) {
      return repo.findById(id);
   }

   @Override
   public Optional<UserEntity> getUserByEmail(String email) {
      return repo.findByEmail(email);
   }

   @Override
   @Transactional
   public UserEntity save(UserEntity user) {
      return repo.save(user);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      repo.deleteById(id);
   }
}
