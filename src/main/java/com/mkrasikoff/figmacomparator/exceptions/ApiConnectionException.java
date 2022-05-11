package com.mkrasikoff.figmacomparator.exceptions;

public class ApiConnectionException extends RuntimeException {

   public ApiConnectionException(String errorMessage) {

      super(errorMessage);
   }
}
