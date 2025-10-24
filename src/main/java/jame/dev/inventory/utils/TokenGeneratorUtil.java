package jame.dev.inventory.utils;

import java.security.SecureRandom;
import java.util.Base64;

public final class TokenGeneratorUtil {

   private static final SecureRandom r = new SecureRandom();
   public static String generate(){
      byte[] bytes = new byte[4];
      r.nextBytes(bytes);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
   }
}
