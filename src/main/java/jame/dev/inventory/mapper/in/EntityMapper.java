package jame.dev.inventory.mapper.in;

@FunctionalInterface
public interface EntityMapper <E, D>{
   E mapToEntity(D dto);
}
