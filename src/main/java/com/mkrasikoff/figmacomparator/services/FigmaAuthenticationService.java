package com.mkrasikoff.figmacomparator.services;

public interface FigmaAuthenticationService {

   String getSessionToken(String username, String password);
}
