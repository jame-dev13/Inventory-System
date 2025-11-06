package jame.dev.inventory.cache;

import lombok.Getter;

@Getter
public enum CacheKeys {

   CUSTOMERS("customers:list"),
   PRODUCTS("products:list"),
   PROVIDERS("providers:list"),
   SALE_ORDERS("sale:orders:list"),
   SALES("sales:list"),
   USERS("users:list"),
   EMPLOYEES("employees:list");

   private final String name;
   CacheKeys(String name){
      this.name = name;
   }
}
