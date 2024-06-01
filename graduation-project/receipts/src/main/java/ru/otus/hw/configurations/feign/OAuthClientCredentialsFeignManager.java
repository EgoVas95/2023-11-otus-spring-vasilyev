package ru.otus.hw.configurations.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import static java.util.Objects.isNull;

@Slf4j
public class OAuthClientCredentialsFeignManager {

    private final OAuth2AuthorizedClientManager manager;

    private final Authentication principal;

    private final ClientRegistration clientRegistration;

    public OAuthClientCredentialsFeignManager(OAuth2AuthorizedClientManager manager,
                                              ClientRegistration clientRegistration) {
        this.manager = manager;
        this.clientRegistration = clientRegistration;
        this.principal = createPrincipal();
    }

    private Authentication createPrincipal() {
        return new FeignAuthentication(clientRegistration);
    }

    public String getAccessToken() {
        try {
            OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(clientRegistration.getRegistrationId())
                    .principal(principal)
                    .build();
            OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);
            if (isNull(client)) {
                throw new IllegalStateException(
                        "client credentials flow on %s failed, client is null"
                                .formatted(clientRegistration.getRegistrationId()));
            }
            return client.getAccessToken().getTokenValue();
        } catch (Exception exp) {
            log.error("client credentials error " + exp.getMessage());
        }
        return null;
    }
}
