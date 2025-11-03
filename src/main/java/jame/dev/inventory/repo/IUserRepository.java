package jame.dev.inventory.repo;

import jame.dev.inventory.models.UserEntity;

import java.util.Optional;

public interface IUserRepository extends CustomJpaRepository<UserEntity, Long> {
   Optional<UserEntity> findByEmail(String email);
}