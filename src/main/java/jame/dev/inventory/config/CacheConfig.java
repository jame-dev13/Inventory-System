package jame.dev.inventory.config;

import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.cache.out.CacheImp;
import jame.dev.inventory.dtos.customer.out.CustomerDto;
import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.dtos.product.out.ProductDto;
import jame.dev.inventory.dtos.provider.out.ProviderDto;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.dtos.user.out.UserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
public class CacheConfig {

   private final JedisPooled jedis;

   public CacheConfig(JedisPooled jedis) {
      this.jedis = jedis;
   }

   @Bean
   public Cache<ProductDto> productDtoCache() {
      return new CacheImp<>(jedis, ProductDto.class);
   }

   @Bean
   public Cache<ProviderDto> providerDtoCache() {
      return new CacheImp<>(jedis, ProviderDto.class);
   }

   @Bean
   public Cache<CustomerDto> customerDtoCache() {
      return new CacheImp<>(jedis, CustomerDto.class);
   }

   @Bean
   public Cache<SaleOrderDto> saleOrderDtoCache() {
      return new CacheImp<>(jedis, SaleOrderDto.class);
   }

   @Bean
   public Cache<SaleDto> saleDtoCache() {
      return new CacheImp<>(jedis, SaleDto.class);
   }

   @Bean
   public Cache<EmployeeDto> employeeDtoCache() {
      return new CacheImp<>(jedis, EmployeeDto.class);
   }

   @Bean
   public Cache<UserDto> userDtoCache() {
      return new CacheImp<>(jedis, UserDto.class);
   }

}
