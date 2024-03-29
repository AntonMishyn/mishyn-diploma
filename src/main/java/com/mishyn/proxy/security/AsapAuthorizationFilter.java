package com.mishyn.proxy.security;

import com.atlassian.asap.api.Jwt;
import com.atlassian.asap.api.exception.CannotRetrieveKeyException;
import com.atlassian.asap.api.exception.InvalidTokenException;
import com.atlassian.asap.core.validator.JwtValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
@RequiredArgsConstructor
public class AsapAuthorizationFilter extends OncePerRequestFilter {

    private final JwtValidator jwtValidator;
    private final AccessConfig accessConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Jwt token = jwtValidator.readAndValidate(
                    httpServletRequest.getHeader("Authorization").substring("Bearer ".length())
            );
            this.validateEndpointAccess(
                    token.getClaims().getIssuer(),
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI()
            );
        } catch (NullPointerException e) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write(convertObjectToJson("No Authorization header"));
            return;
        } catch (InvalidTokenException | ForbiddenURLException | IOException | CannotRetrieveKeyException e) {
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpServletResponse.getWriter().write(convertObjectToJson(e.getMessage()));
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        List<String> whitelistedURLs = Arrays.asList(
                "/api/v2/api-docs",
                "/api/swagger-resources",
                "/api/swagger-resources/",
                "/api/configuration/ui",
                "/api/configuration/security",
                "/api/swagger-ui",
                "/api/webjars/"
        );

        for (String url : whitelistedURLs) {
            if (path.startsWith(url)) {
                return true;
            }
        }

        return false;
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private void validateEndpointAccess(String issuer, String method, String requestURL)
            throws IOException, ForbiddenURLException {
        Map<String, List<String>> accessibleEndpointsForIssuer = accessConfig.getAccessibleEndpointsForIssuer(issuer);
        List<String> accessibleEndpointByRequestMethod = accessibleEndpointsForIssuer.get(method);

        accessibleEndpointByRequestMethod.stream()
                .filter(url -> new AntPathMatcher().match(url, requestURL))
                .findFirst()
                .orElseThrow(() -> new ForbiddenURLException("Access forbidden for issuer " + issuer));
    }
}
