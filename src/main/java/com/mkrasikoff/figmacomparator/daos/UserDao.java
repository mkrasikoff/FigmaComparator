package com.mkrasikoff.figmacomparator.daos;

import com.mkrasikoff.figmacomparator.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

   private String actualToken;
   private boolean isLoggedIn = false;

   public String getActualToken() {
      return actualToken;
   }

   public void saveActualToken(User user) {
      actualToken = user.getToken();
   }

   public boolean isLoggedIn() {
      return isLoggedIn;
   }

   public void setLoggedIn(boolean isLoggedIn) {
      this.isLoggedIn = isLoggedIn;
   }
}
