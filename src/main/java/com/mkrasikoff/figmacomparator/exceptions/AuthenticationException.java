package com.mkrasikoff.figmacomparator.exceptions;

public class AuthenticationException extends RuntimeException {

   public AuthenticationException(String errorMessage, Throwable err) {

      super(errorMessage, err);
   }

   public AuthenticationException(String errorMessage) {

      super(errorMessage);
   }
}
