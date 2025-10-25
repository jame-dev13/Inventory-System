package jame.dev.inventory.service.in;

public interface TokenService {
   void save(String token, long ttl);
   boolean contains(String token);
}
