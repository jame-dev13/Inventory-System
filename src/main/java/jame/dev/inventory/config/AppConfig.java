package jame.dev.inventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
public class AppConfig {

   @Value("${redis.url}")
   private String url;

   @Bean
   public JedisPooled jedisPooled() {
      return new JedisPooled(url);
   }
}