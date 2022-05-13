package com.mkrasikoff.figmacomparator.services;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpServiceImplTest {

   private static String LOGIN_BAD_TOKEN = "Some bad token";

   private HttpService httpService = new HttpServiceImpl();

   @Test
   void doGet_badTokenIsGiven_authenticationExceptionWasThrown() {

      // WHEN
      AuthenticationException exc = assertThrows(AuthenticationException.class,
              () -> httpService.doGet(FigmaAPI.INFO_ABOUT_ME, LOGIN_BAD_TOKEN));

      // THEN
      assertEquals("Invalid token!", exc.getMessage());
   }
}
