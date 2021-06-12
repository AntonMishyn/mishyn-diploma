package com.mishyn.proxy.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccessConfig {

    private final String ACCESS_CONFIG_PREFIX = "access/";

    public Map<String, List<String>> getAccessibleEndpointsForIssuer(String issuer) throws IOException {
       return new ObjectMapper().readValue(
               new ClassPathResource(ACCESS_CONFIG_PREFIX + issuer + ".json").getFile(),
               new TypeReference<HashMap<String, List<String>>>() {}
       );
    }
}
