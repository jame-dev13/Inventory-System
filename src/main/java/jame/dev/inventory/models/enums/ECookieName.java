package jame.dev.inventory.models.enums;

import lombok.Getter;

@Getter
public enum ECookieName {
   JWT_ACCESS("_HOST-JWT_ACCESS"), JWT_REFRESH("_HOST-JWT_REFRESH");
   private final String name;
    ECookieName(String name){
      this.name = name;
   }

}
