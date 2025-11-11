package jame.dev.inventory.cache.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jame.dev.inventory.cache.in.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPooled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CacheImp<T> implements Cache<T> {

   private static final Logger log = LoggerFactory.getLogger(CacheImp.class);
   private final JedisPooled jedis;
   private final Class<T> type;
   private final ObjectMapper mapper = new ObjectMapper()
           .registerModule(new JavaTimeModule());

   public CacheImp(JedisPooled jedisPooled, Class<T> type) {
      this.jedis = jedisPooled;
      this.type = type;
   }

   @Override
   public Optional<List<T>> getCache(String key) {
      try {
         List<String> json = jedis.lrange(key, 0, -1);
         if (json.isEmpty()) return Optional.empty();
         List<T> list = new ArrayList<>();
         for (String s : json) {
            list.add(mapper.readValue(s, type));
         }
         Collections.reverse(list);
         return Optional.of(list);
      } catch (JsonProcessingException e) {
         return Optional.empty();
      }
   }

   @Override
   public Optional<T> getElement(String key, String element) {
      List<String> json = jedis.lrange(key, 0, -1);
      String record = null;
      boolean found = false;
      for (String jsonValue : json) {
         if(jsonValue.contains(element)){
            found = true;
            record = jsonValue;
            break;
         }
      }
      if(!found){
         return Optional.empty();
      }

      try{
         T jsonParsed = mapper.readValue(record, type);
         return Optional.of(jsonParsed);
      }catch (JsonProcessingException e){
         log.error("Can't write json. ", e);
         return Optional.empty();
      }
   }

   @Override
   public void saveCache(String key, List<T> t) {
      try {
         jedis.del(key);
         for (T type : t) {
            jedis.rpush(key, mapper.writeValueAsString(type));
         }
         jedis.expire(key, 420);
      } catch (JsonProcessingException e) {
         log.error("Can't write the json value. ", e);
      }
   }

   @Override
   public void addData(String key, T t) {
      try {
         List<String> items = jedis.lrange(key, 0, -1);
         if (!items.contains(mapper.writeValueAsString(t))) {
            jedis.rpush(key, mapper.writer().writeValueAsString(t));
            System.out.println("item added to the cache.");
         }
      } catch (JsonProcessingException e) {
         log.error("Can't write json value: ", e);
      }
   }

   @Override
   public void updateData(String key, Predicate<T> filter, T t) {
      try {
         List<String> items = jedis.lrange(key, 0, -1);
         for (int i = 0; i < items.size(); i++) {
            T item = mapper.readValue(items.get(i), type);
            if (filter.test(item)) {
               jedis.lset(key, i, mapper.writeValueAsString(t));
               long ttl = jedis.expireTime(key);
               if (ttl <= 0) jedis.expire(key, 420);
               System.out.println("cache item updated.");
               break;
            }
         }
      } catch (JsonProcessingException e) {
         log.error("Error while read/write json values: ", e);
      }
   }

   @Override
   public void removeData(String key, Predicate<T> filter, T t) {
      try {
         List<String> items = jedis.lrange(key, 0, -1);
         for (String s : items) {
            T item = mapper.readValue(s, type);
            if (filter.test(item)) {
               jedis.lrem(key, 1, mapper.writeValueAsString(t));
               System.out.println("item removed from the cache.");
               break;
            }
         }
      }catch (JsonProcessingException e){
         log.error("Error while read/write json values: ", e);
      }
   }

   @Override
   public boolean contains(String key) {
      return jedis.exists(key);
   }

   @Override
   public void clear(String key) {
      jedis.del(key);
   }
}
