package jame.dev.inventory.mapper.in;

public interface DtoMapper<D, E> {
   D mapToDto(E entity);
}
