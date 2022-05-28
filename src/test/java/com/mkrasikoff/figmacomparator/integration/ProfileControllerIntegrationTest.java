package com.mkrasikoff.figmacomparator.integration;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import com.mkrasikoff.figmacomparator.integration.data.User;
import com.mkrasikoff.figmacomparator.integration.helpers.IntegrationTest;
import com.mkrasikoff.figmacomparator.integration.helpers.TestHelper;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.mkrasikoff.figmacomparator.integration.helpers.TestHelper.givenUrl;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfileControllerIntegrationTest extends IntegrationTest {

   @Test
   public void getProfileInfo_tokenIsGiven_userInformationReturned() {

      // WHEN
      User user = givenUrl().headers(TestHelper.getBasicHeaderStore(token))
              .get(FigmaAPI.INFO_ABOUT_ME)
              .then()
              .statusCode(HttpStatus.SC_OK)
              .extract()
              .response()
              .getBody()
              .as(User.class);

      // THEN
      assertNotNull(user.getId());
      assertNotNull(user.getEmail());
      assertNotNull(user.getUsername());
      assertNotNull(user.getImgUrl());
   }

   @Test
   public void getProfileInfo_tokenIsNotGiven_forbiddenError() {

      // WHEN-THEN
      givenUrl()
              .get(FigmaAPI.INFO_ABOUT_ME)
              .then()
              .statusCode(HttpStatus.SC_FORBIDDEN);
   }

   @Test
   public void getProfileInfo_tokenIsNotValid_forbiddenError() {

      // GIVEN
      String notValidToken = "Some token";

      // WHEN-THEN
      givenUrl().headers(TestHelper.getBasicHeaderStore(notValidToken))
              .get(FigmaAPI.INFO_ABOUT_ME)
              .then()
              .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
