package jame.dev.inventory.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TokenGeneratorUtilTest {

   @Test
   @DisplayName("Token length is 6")
   void length() {
      assertEquals(6, TokenGeneratorUtil.generate().length());
      assertEquals(6, TokenGeneratorUtil.generate().length());
      assertEquals(6, TokenGeneratorUtil.generate().length());
      assertEquals(6, TokenGeneratorUtil.generate().length());
      assertEquals(6, TokenGeneratorUtil.generate().length());
   }
}