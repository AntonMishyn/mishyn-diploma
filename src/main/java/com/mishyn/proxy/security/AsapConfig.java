package com.mishyn.proxy.security;

import com.atlassian.asap.core.keys.PemReader;
import com.atlassian.asap.core.keys.publickey.FilePublicKeyProvider;
import com.atlassian.asap.core.parser.JwtParser;
import com.atlassian.asap.core.validator.JwtClaimsValidator;
import com.atlassian.asap.core.validator.JwtValidator;
import com.atlassian.asap.core.validator.JwtValidatorImpl;
import com.atlassian.asap.nimbus.parser.NimbusJwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.time.Clock;

@Configuration
@RequiredArgsConstructor
public class AsapConfig {

    @Value("${asap.public-key-repo}")
    private String publicKeyRepo;
    @Value("${asap.audience}")
    private String audience;

    private final IssuersMapping issuersMapping;

    @Bean
    public JwtParser jwtParser() {
        return new NimbusJwtParser();
    }

    @Bean
    public JwtValidator jwtValidator() {
        return new JwtValidatorImpl(
                new FilePublicKeyProvider(new File(publicKeyRepo), new PemReader()),
                new NimbusJwtParser(),
                new JwtClaimsValidator(Clock.systemUTC()),
                audience
        );
    }
}
