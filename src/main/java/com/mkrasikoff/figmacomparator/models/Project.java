package com.mkrasikoff.figmacomparator.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Project {

   @NotEmpty(message = "Key should not be empty!")
   private String key;

   @NotEmpty(message = "Name should not be empty!")
   private String name;

   public Project(String key, String name) {
      this.key = key;
      this.name = name;
   }

   public Project() {

   }
}
