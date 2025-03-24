package com.project.forms.config;

import com.project.forms.dao.response.UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static com.project.forms.utils.APIConstants.OAUTH2_USERINFO_PATH;
import static com.project.forms.utils.Constants.NAME;
import static com.project.forms.utils.Constants.SUB;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.ACCESS_TOKEN;

public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;

    public GoogleOpaqueTokenIntrospector(WebClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserInfo userInfo = userInfoClient.get()
                .uri(uriBuilder -> uriBuilder.path(OAUTH2_USERINFO_PATH)
                        .queryParam(ACCESS_TOKEN, token).build())
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(SUB, userInfo.sub());
        attributes.put(NAME, userInfo.name());

        return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.name(), attributes, null);
    }
}
