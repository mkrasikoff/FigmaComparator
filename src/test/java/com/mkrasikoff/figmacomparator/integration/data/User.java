package com.mkrasikoff.figmacomparator.integration.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class User {

   @JsonProperty("id")
   private String id;

   @JsonProperty("email")
   private String email;

   @JsonProperty("handle")
   private String username;

   @JsonProperty("img_url")
   private String imgUrl;

   public String getId() {
      return id;
   }

   public String getEmail() {
      return email;
   }

   public String getUsername() {
      return username;
   }

   public String getImgUrl() {
      return imgUrl;
   }
}
