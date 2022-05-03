package com.mkrasikoff.figmacomparator.services;

import com.mkrasikoff.figmacomparator.exceptions.ApiConnectionException;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class HttpServiceImpl implements HttpService {

   public static final String BAD_CREDENTIALS_MESSAGE = "Bad credentials!";
   public static final String STATUS_MESSAGE = "Status: ";
   public static final String JSESSIONID_COOKIE_KEY = "JSESSIONID";
   public static final String FIGMA_API_DOMAIN = "api.figma.com";
   private static final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);

   @Override
   public String doGet(String url, String token) {

      String jsonResponse = null;
      HttpGet httpGet = new HttpGet(url);

      try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(getBasicCookieStore(token)).build();
           CloseableHttpResponse response = client.execute(httpGet)
      ) {
         jsonResponse = checkResponse(response);
      }
      catch (IOException exc) {
         logger.error(exc.getMessage());
      }

      return jsonResponse;
   }

   public BasicCookieStore getBasicCookieStore(String jSessionId) {

      BasicCookieStore cookieStore = new BasicCookieStore();
      BasicClientCookie jSessionCookie = new BasicClientCookie(JSESSIONID_COOKIE_KEY, jSessionId);
      jSessionCookie.setDomain(FIGMA_API_DOMAIN);
      jSessionCookie.setPath("/");
      cookieStore.addCookie(jSessionCookie);

      return cookieStore;
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

   String checkResponse(CloseableHttpResponse response) throws IOException {

      HttpEntity responseEntity = response.getEntity();
      int responseStatus = response.getStatusLine().getStatusCode();

      if (responseStatus == HttpStatus.SC_OK) {
         return IOUtils.toString(responseEntity.getContent(), StandardCharsets.UTF_8);
      }
      else if (responseStatus == HttpStatus.SC_UNAUTHORIZED) {
         logger.error(BAD_CREDENTIALS_MESSAGE);
         throw new AuthenticationException(BAD_CREDENTIALS_MESSAGE);
      }
      else {
         logger.error(STATUS_MESSAGE + responseStatus);
         throw new ApiConnectionException(STATUS_MESSAGE + responseStatus);
      }
   }
}
