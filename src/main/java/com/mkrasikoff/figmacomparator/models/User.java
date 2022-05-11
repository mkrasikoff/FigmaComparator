package com.mkrasikoff.figmacomparator.models;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class User {

   @Pattern(regexp = "([A-Za-z0-9]{6}-[A-Za-z0-9]{8}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{12})", message = "Right format token required!")
   private String token;
}
