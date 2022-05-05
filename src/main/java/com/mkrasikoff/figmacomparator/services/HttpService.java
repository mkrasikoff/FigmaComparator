package com.mkrasikoff.figmacomparator.services;

public interface HttpService {

   String doGet(String url, String token);

   String doPost(String url, String body);
}
