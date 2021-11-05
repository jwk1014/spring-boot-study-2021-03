package com.example.demo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
public class ApiLogFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final ZonedDateTime startTime = ZonedDateTime.now();
        ApiLog.ApiLogBuilder apiLogBuilder = ApiLog.builder()
                .time(startTime);

        ContentCachingRequestWrapper wrappedRequest = request instanceof ContentCachingRequestWrapper
                ? (ContentCachingRequestWrapper) request
                : new ContentCachingRequestWrapper(request);

        ContentCachingResponseWrapper wrappedResponse = response instanceof ContentCachingResponseWrapper
                ? (ContentCachingResponseWrapper) response
                : new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            apiLogBuilder.duration(ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()));

            try {
                wrappedResponse.copyBodyToResponse();
            } catch (IOException ignored) { }

            apiLogBuilder
                    .method(wrappedRequest.getMethod())
                    .path(wrappedRequest.getRequestURI())
                    .patternPath((String) wrappedRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))
                    .statusCode(wrappedResponse.getStatus());

            try {
                log.info(objectMapper.writeValueAsString(apiLogBuilder.build()));
            } catch (Exception ignored) { }
        }
    }

    @Builder
    @Getter
    static class ApiLog {
        private final String logType = "API";
        private ZonedDateTime time;
        private final Long duration;
        private final String method;
        private final String path;
        private final String patternPath;
        private final Integer statusCode;
    }
}
