package com.mkrasikoff.figmacomparator.draft;

// TODO: Finish this service in the task FC-## - Add OAuthV2 Authentication
public interface FigmaAuthenticationService {

   String getSessionToken(String username, String password);
}
