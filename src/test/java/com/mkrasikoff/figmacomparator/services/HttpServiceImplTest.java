package com.mkrasikoff.figmacomparator.services;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpServiceImplTest {

   private static String LOGIN_BAD_TOKEN = "Some bad token";
   private static String LOGIN_RIGHT_TOKEN = "383055-c1692fb4-5772-4eab-899a-da5a01666088";

   private final HttpService httpService = new HttpServiceImpl();

   @Test
   void doGet_badTokenIsGiven_authenticationExceptionWasThrown() {

      // WHEN
      AuthenticationException exc = assertThrows(AuthenticationException.class,
              () -> httpService.doGet(FigmaAPI.INFO_ABOUT_ME, LOGIN_BAD_TOKEN));

      // THEN
      assertEquals("Invalid token!", exc.getMessage());
   }

   @Test
   void doGet_goodTokenIsGiven_jsonResponseWasReturned() {

      // WHEN
      String result = httpService.doGet(FigmaAPI.INFO_ABOUT_ME, LOGIN_RIGHT_TOKEN);

      // THEN
      assertInstanceOf(String.class, result);
   }
}
