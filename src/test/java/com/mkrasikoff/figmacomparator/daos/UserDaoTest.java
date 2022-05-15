package com.mkrasikoff.figmacomparator.daos;

import com.mkrasikoff.figmacomparator.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoTest {

   private static final String TOKEN = "Some token";
   private static final String EMPTY = "";

   private final UserDao userDao = new UserDao();

   @Test
   void getActualUser_newUserIsGiven_actualUserAndNewAreSame() {

      // GIVEN
      User newUser = new User();
      newUser.setToken(TOKEN);
      userDao.saveActualToken(newUser);

      // WHEN
      User actualUser = userDao.getActualUser();

      // THEN
      assertEquals(newUser, actualUser);
      assertEquals(TOKEN, actualUser.getToken());
   }

   @Test
   void getActualUser_userIsNotGiven_userWithEmptyTokenReturned() {

      // WHEN
      User actualUser = userDao.getActualUser();

      // THEN
      assertEquals(EMPTY, actualUser.getToken());
   }
}
