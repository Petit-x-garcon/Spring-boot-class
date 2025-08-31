package com.sambat.demo.Common.Filter;

import com.sambat.demo.Common.Constant.RequestConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        try {
            String requestId = UUID.randomUUID().toString();
            MDC.put(RequestConstant.REQUEST_ID, requestId);

            String httpMethod = httpRequest.getMethod();
            MDC.put(RequestConstant.HTTP_METHOD, httpMethod);

            String requestUri = httpRequest.getRequestURI();
            String requestQuery = httpRequest.getQueryString();
            String fullPath = requestQuery != null ? requestUri.concat("?").concat(requestQuery) : requestUri;
            MDC.put(RequestConstant.REQUEST_PATH, fullPath);

            filterChain.doFilter(servletRequest, servletResponse);
        }
        finally {
            MDC.clear();
        }
    }
}
