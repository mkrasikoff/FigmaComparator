package com.mkrasikoff.figmacomparator.services;

import com.mkrasikoff.figmacomparator.exceptions.ApiConnectionException;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import com.mkrasikoff.figmacomparator.services.helpers.LoginTokenHeaderImpl;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpServiceImpl implements HttpService {

   private static final String MESSAGE_INVALID_TOKEN = "Invalid token!";
   private static final String MESSAGE_BAD_CREDENTIALS = "Bad credentials!";
   private static final String MESSAGE_ERROR_STATUS = "Error status: ";

   private static final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);

   @Override
   public String doGet(String url, String token) {

      String jsonResponse = null;
      HttpGet httpGet = new HttpGet(url);

      try (CloseableHttpClient client = HttpClientBuilder.create()
              .setDefaultHeaders(getBasicHeaderStore(token))
              .build();
           CloseableHttpResponse response = client.execute(httpGet)
      ) {
         jsonResponse = checkResponse(response);
      }
      catch (IOException exc) {
         logger.error(exc.getMessage());
      }

      return jsonResponse;
   }

   private List<Header> getBasicHeaderStore(String token) {
      List<Header> headers = new ArrayList<>();
      Header header = new LoginTokenHeaderImpl(token);
      headers.add(header);

      return headers;
   }

   private String checkResponse(CloseableHttpResponse response) throws IOException {

      HttpEntity responseEntity = response.getEntity();
      int responseStatus = response.getStatusLine().getStatusCode();

      if (responseStatus == HttpStatus.SC_OK) {
         return IOUtils.toString(responseEntity.getContent(), StandardCharsets.UTF_8);
      }
      else if (responseStatus == HttpStatus.SC_FORBIDDEN) {
         logger.error(MESSAGE_INVALID_TOKEN);
         throw new AuthenticationException(MESSAGE_INVALID_TOKEN);
      }
      else if (responseStatus == HttpStatus.SC_UNAUTHORIZED) {
         logger.error(MESSAGE_BAD_CREDENTIALS);
         throw new AuthenticationException(MESSAGE_BAD_CREDENTIALS);
      }
      else {
         String message = MESSAGE_ERROR_STATUS + responseStatus;
         logger.error(message);

         throw new ApiConnectionException(MESSAGE_ERROR_STATUS + responseStatus);
      }
   }

   @Override
   // TODO: Finish this service in the task FC-## - Perform post service for Figma API
   public String doPost(String url, String body) {
      return null;
   }
}
