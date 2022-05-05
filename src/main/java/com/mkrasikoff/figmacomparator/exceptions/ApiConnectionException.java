package com.mkrasikoff.figmacomparator.exceptions;

public class ApiConnectionException extends RuntimeException {

   public ApiConnectionException(String errorMessage, Throwable err) {

      super(errorMessage, err);
   }

   public ApiConnectionException(String errorMessage) {

      super(errorMessage);
   }
}
