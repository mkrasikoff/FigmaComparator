package com.mkrasikoff.figmacomparator.services.helpers;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;

public class LoginTokenHeaderImpl implements Header {

   private final String token;

   @Override
   public HeaderElement[] getElements() throws ParseException {
      return new HeaderElement[0];
   }

   @Override
   public String getName() {
      return "X-Figma-Token";
   }

   @Override
   public String getValue() {
      return token;
   }

   public LoginTokenHeaderImpl(String tokenValue) {
      token = tokenValue;
   }
}
