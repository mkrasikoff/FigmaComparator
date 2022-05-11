package com.mkrasikoff.figmacomparator.exceptions;

public class ProjectNotFoundException extends RuntimeException {

   public ProjectNotFoundException(String errorMessage, Throwable err) {

      super(errorMessage, err);
   }

   public ProjectNotFoundException(String errorMessage) {

      super(errorMessage);
   }
}
