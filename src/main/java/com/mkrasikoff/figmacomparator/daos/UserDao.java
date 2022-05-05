package com.mkrasikoff.figmacomparator.daos;

import com.mkrasikoff.figmacomparator.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

   private String actualToken;

   public String getActualToken() {
      return actualToken;
   }

   public void saveActualToken(User user) {
      actualToken = user.getToken();
   }
}
