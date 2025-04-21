package com.example.keycloak.site1_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenRestController {

    @Autowired
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/get-token")
    public ResponseEntity<Map> getToken(@AuthenticationPrincipal OidcUser oidcUser, Authentication authentication) {
        Map result = new HashMap();

        try {
            String userId = (String) oidcUser.getUserInfo().getClaims().get("preferred_username");
            String userName = (String) oidcUser.getUserInfo().getClaims().get("name");

            //
            OAuth2AuthorizedClient auth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(), authentication.getName());
            OAuth2AccessToken oAuth2AccessToken = auth2AuthorizedClient.getAccessToken();
            OAuth2RefreshToken oAuth2RefreshToken = auth2AuthorizedClient.getRefreshToken();

            String idToken = oidcUser.getIdToken().getTokenValue();
            String accessToken = oAuth2AccessToken.getTokenValue();
            String refreshToken = oAuth2RefreshToken.getTokenValue();
            //
            result.put("result", "OK");
            result.put("msg", "SUCCESS");
            result.put("userId", userId);
            result.put("userName", userName);
            result.put("id_token", idToken);
            result.put("access_token", accessToken);
            result.put("refresh_token", refreshToken);
        } catch (Exception e) {
            result.put("result", "NOT_OK");
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
