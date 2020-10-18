package org.istio.sts.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CustomJWTTokenEnhancer extends JwtAccessTokenConverter {


	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		Map info = new HashMap();
		info.put("iat", Instant.now().getEpochSecond());
		info.put("sub", authentication.getUserAuthentication().getName());
		info.put("iss", "sts.ecomm.com");
		info.put("aud", "*.ecomm.com");
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}