package com.mkrasikoff.figmacomparator.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FigmaAuthenticationServiceImpl implements FigmaAuthenticationService {

   private static final Logger logger = LoggerFactory.getLogger(FigmaAuthenticationServiceImpl.class);
   private static final String MESSAGE_WRONG_JSON_RESPONSE = "Error while getting session, wrong Json response.";
   private static final String AUTH_URL = "https://www.figma.com/oauth";
   public static final String SESSION_FIELD_NAME = "session";
   public static final String VALUE_FIELD_NAME = "value";
   private final HttpService httpService;

   public FigmaAuthenticationServiceImpl(HttpService httpService) {

      this.httpService = httpService;
   }

   public String getSessionToken(String username, String password) {

      String jsonCredentialsBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

      String responseJson = httpService.doPost(AUTH_URL, jsonCredentialsBody);

      if (responseJson == null) {
         logger.error(MESSAGE_WRONG_JSON_RESPONSE);

         return null;
      }

      JSONObject jsonObject = new JSONObject(responseJson);

      return jsonObject.getJSONObject(SESSION_FIELD_NAME).getString(VALUE_FIELD_NAME);
   }
}
