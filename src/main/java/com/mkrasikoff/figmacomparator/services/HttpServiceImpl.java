package com.mkrasikoff.figmacomparator.services;

import com.mkrasikoff.figmacomparator.exceptions.ApiConnectionException;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpServiceImpl implements HttpService {

   public static final String MESSAGE_INVALID_TOKEN = "Invalid token!";
   public static final String MESSAGE_BAD_CREDENTIALS = "Bad credentials!";
   public static final String MESSAGE_STATUS = "Status: ";

   public static final String JSESSIONID_COOKIE_KEY = "JSESSIONID";
   public static final String FIGMA_API_DOMAIN = "api.figma.com";

   private static final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);

   @Override
   public String doGet(String url, String token) {

      String jsonResponse = null;
      HttpGet httpGet = new HttpGet(url);

      try (CloseableHttpClient client = HttpClientBuilder.create()
              .setDefaultHeaders(getBasicHeaderStore(token))
              .setDefaultCookieStore(getBasicCookieStore(token))
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

   public List<Header> getBasicHeaderStore(String token) {
      List<Header> headers = new ArrayList<>();

      Header header = new Header() {

         @Override
         public HeaderElement[] getElements() throws ParseException {
            return new HeaderElement[0];
         }

         @Override
         public String getName() {
            return "X-Figma-Token";
         }

         @Override
         public String getValue() {
            return token;
         }
      };

      headers.add(header);

      return headers;
   }

   public BasicCookieStore getBasicCookieStore(String jSessionId) {

      BasicCookieStore cookieStore = new BasicCookieStore();
      BasicClientCookie jSessionCookie = new BasicClientCookie(JSESSIONID_COOKIE_KEY, jSessionId);
      jSessionCookie.setDomain(FIGMA_API_DOMAIN);
      jSessionCookie.setPath("/");
      cookieStore.addCookie(jSessionCookie);

      return cookieStore;
   }

   String checkResponse(CloseableHttpResponse response) throws IOException {

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
         logger.error(MESSAGE_STATUS + responseStatus);
         throw new ApiConnectionException(MESSAGE_STATUS + responseStatus);
      }
   }

   @Override
   public String doPost(String url, String body) {

      HttpPost httpPost = new HttpPost(url);
      StringEntity entity = null;

      try {
         entity = new StringEntity(body);
      }
      catch (UnsupportedEncodingException exc) {
         logger.error(exc.getMessage());
      }

      httpPost = setPostJsonHeaders(httpPost);
      httpPost.setEntity(entity);
      String jsonResponse = null;

      try (CloseableHttpClient client = HttpClients.createDefault();
           CloseableHttpResponse response = client.execute(httpPost)) {
         jsonResponse = checkResponse(response);
      }
      catch (IOException exc) {
         logger.error(exc.getMessage());
      }

      return jsonResponse;
   }

   HttpPost setPostJsonHeaders(HttpPost httpPost) {

      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");

      return httpPost;
   }
}
