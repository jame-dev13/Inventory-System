package jame.dev.inventory.mapper.in;

public interface InputMapper <E, D>{
   E inputToEntity(D dto);
}
