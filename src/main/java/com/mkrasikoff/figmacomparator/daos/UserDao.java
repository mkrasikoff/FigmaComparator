package com.mkrasikoff.figmacomparator.daos;

import com.mkrasikoff.figmacomparator.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

   private User actualUser;
   private boolean isLoggedIn = false;

   public User getActualUser() {
      return actualUser;
   }

   public void saveActualToken(User user) {
      actualUser = user;
   }

   public boolean isLoggedIn() {
      return isLoggedIn;
   }

   public void setLoggedIn(boolean isLoggedIn) {
      this.isLoggedIn = isLoggedIn;
   }
}
