package com.mkrasikoff.figmacomparator.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class User {

   @NotEmpty(message = "Token should not be empty!")
   @Size(min = 43, max = 43, message = "Please sure that you write a valid token! (43 symbols)")
   private String token;
}
