package com.mishyn.proxy.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "asap")
public class IssuersMapping {

    private Map<String, String> issuersMapping;
}
