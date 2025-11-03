package jame.dev.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

   @Query("SELECT e FROM #{#entityName} e WHERE e.active = true")
   List<T> findAllActives();

   @Modifying
   @Transactional
   @Query("UPDATE #{#entityName} e SET e.active = false WHERE e.id = :id")
   void softDeleteById(@Param("id") Long id);
}
