package com.robi.oauth.authenticator;

public interface IAuthenticator {
    
    public boolean auth(String access_code);
}