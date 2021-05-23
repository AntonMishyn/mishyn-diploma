package com.mishyn.proxy.client;

import com.atlassian.asap.api.Jwt;
import com.atlassian.asap.api.JwtBuilder;
import com.atlassian.asap.api.SigningAlgorithm;
import com.atlassian.asap.api.client.http.AuthorizationHeaderGenerator;
import com.atlassian.asap.api.exception.CannotRetrieveKeyException;
import com.atlassian.asap.api.exception.InvalidTokenException;
import com.atlassian.asap.core.client.http.AuthorizationHeaderGeneratorImpl;
import com.atlassian.asap.core.parser.JwtParser;
import com.mishyn.proxy.security.IssuersMapping;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeignInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${asap.proxy.issuer}")
    private String issuer;
    @Value("${asap.proxy.audience}")
    private String audience;
    @Value("${asap.proxy.private-key-id}")
    private String privateKeyId;
    @Value("${asap.proxy.private-key-path}")
    private String privateKeyRepo;

    private final JwtParser jwtParser;
    private final IssuersMapping issuersMapping;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        AuthorizationHeaderGenerator authorizationHeaderGenerator =
                AuthorizationHeaderGeneratorImpl.createDefault(URI.create(privateKeyRepo));
        Jwt jwtPrototype = JwtBuilder.newJwt()
                .keyId(privateKeyId)
                .algorithm(SigningAlgorithm.RS256)
                .audience(audience)
                .subject(Optional.ofNullable(getRequestIssuer()))
                .issuer(issuer)
                .build();

        Jwt jwt = JwtBuilder.newFromPrototype(jwtPrototype).build();

        try {
            requestTemplate.header("Authorization", authorizationHeaderGenerator.generateAuthorizationHeader(jwt));
        } catch (InvalidTokenException e) {
            throw new RuntimeException("Invalid prototype token", e);
        } catch (CannotRetrieveKeyException e) {
            e.printStackTrace();
        }
    }

    private String getRequestIssuer() {
        try {
            Jwt validJwt = jwtParser.parse(
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                            .getRequest().getHeader("Authorization")
                            .substring("Bearer ".length())
            );
            return issuersMapping.getIssuersMapping().get(validJwt.getClaims().getIssuer());
        } catch (NullPointerException | InvalidTokenException e) {
            return null;
        }
    }
}
