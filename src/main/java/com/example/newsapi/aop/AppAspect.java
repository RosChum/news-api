package com.example.newsapi.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Aspect
@Component
@Slf4j
public class AppAspect {

    @Pointcut("execution(* com.example.newsapi.service.ApiService.getAllNews())")
    public void checkUsers() {
    }


    @Before("checkUsers()")
    public void checkUserAfterGetAllNews() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        log.info(request.toString());

        request.getHeaderNames().asIterator().forEachRemaining(d->log.info(d));
        pathVariables.forEach((k,v) -> log.info(k + "   " + v));


log.info( " request.getAttribute user-agent  "+   request.getAttribute("user-agent"));
        log.info( " request.getAttribute accept  "+   request.getAttribute("accept"));
        log.info( " request.getAttribute postman-token  "+   request.getAttribute("postman-token"));
        log.info( " request.getAttribute host  "+   request.getAttribute("host"));
        log.info( " request.getAttribute accept-encoding  "+   request.getAttribute("accept-encoding"));
        log.info( " request.getAttribute  connection "+   request.getAttribute("connection"));

        log.info(" pathVariables  -  " + pathVariables);

    }


}
