package com.example.newsapi.aop;

import com.example.newsapi.exception.AccessRightsException;
import com.example.newsapi.repository.NewsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private NewsRepository newsRepository;

    @Pointcut("@annotation(com.example.newsapi.annotation.CheckAccessRights)")
    public void checkingAccessRights() {
    }

    @Before("checkingAccessRights()")
    public void checkingAccessRightsAfterUpdateNews() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long newsId = Long.valueOf(pathVariables.get("id"));
        Long authorId = Long.valueOf(pathVariables.get("accountId"));

        if (!newsRepository.findById(newsId).orElseThrow().getAuthor().getId().equals(authorId)) {

            throw new AccessRightsException("Нет прав на редактирование новости");
        }
    }

}
