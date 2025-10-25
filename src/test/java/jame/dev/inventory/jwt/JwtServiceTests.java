package jame.dev.inventory.jwt;

import jame.dev.inventory.jwt.in.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTests {

   @Autowired
   private  JwtService jwtService;

   private String access;
   private String refresh;
   private String weak;

   @BeforeEach
   public void setUp(){
      access = jwtService.generateAccessToken("Angel");
      refresh = jwtService.generateRefreshToken("Angel");
      weak = jwtService.buildToken("Angel", 2000);
   }

   @Test
   @DisplayName("Generates  different tokens.")
   public void different(){
      System.out.println(access);
      System.out.println(refresh);
      Assertions.assertNotEquals(access, refresh, "Tokens should be different");
   }

   @Test
   @DisplayName("Token invalid on expiration")
   public void invalidated() throws InterruptedException {
      Thread.sleep(3000);
      Assertions.assertFalse(jwtService.isTokenValid(weak, "Token should be not valid."));
   }
}
