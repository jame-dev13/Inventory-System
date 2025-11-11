package jame.dev.inventory.cache.in;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Cache<T> {
   Optional<List<T>> getCache(String key);

   Optional<T> getElement(String key, String element);

   void saveCache(String key, List<T> t);

   void addData(String key, T t);

   void updateData(String key, Predicate<T> filter, T t);

   void removeData(String key, Predicate<T> filter, T t);

   boolean contains(String key);

   void clear(String key);
}
