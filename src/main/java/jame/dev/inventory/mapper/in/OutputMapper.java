package jame.dev.inventory.mapper.in;

public interface OutputMapper<D, E>{
   D toDto(E entity);
   E toEntity(D dto);
}
