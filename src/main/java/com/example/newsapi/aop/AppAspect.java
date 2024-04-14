package com.example.newsapi.aop;

import com.example.newsapi.annotation.AccessType;
import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.exception.AccessRightsException;
import com.example.newsapi.exception.UserNotFoundException;
import com.example.newsapi.model.Account;
import com.example.newsapi.model.Role;
import com.example.newsapi.model.RoleType;
import com.example.newsapi.repository.AccountRepository;
import com.example.newsapi.repository.CommentRepository;
import com.example.newsapi.repository.NewsRepository;
import com.example.newsapi.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Set;

@Aspect
@Component
@Slf4j
public class AppAspect {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CommentRepository commentRepository;


    @Before("@annotation(checkAccessRights)")
    public void checkingAccessRightsAfterUpdateNews(CheckAccessRights checkAccessRights) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long id = Long.valueOf(pathVariables.get("id"));
//        Long authorId = Long.valueOf(pathVariables.get("accountId"));

        Account authorizeAccount = accountRepository.findByEmail(SecurityService.getAuthenticationUserEmail()).orElseThrow(() -> new UserNotFoundException("Author not found"));

        if (checkAccessRights.checkAccessType().equals(AccessType.Comment)) {
            Long itemId = commentRepository.findById(id).orElseThrow().getAccount().getId();
            checkRights(itemId, authorizeAccount);
        }
        if (checkAccessRights.checkAccessType().equals(AccessType.News)) {
            Long itemId = newsRepository.findById(id).orElseThrow().getAccount().getId();
            checkRights(itemId, authorizeAccount);
        }
        if (checkAccessRights.checkAccessType().equals(AccessType.Account)) {
            Long itemId = accountRepository.findById(id).orElseThrow().getId();
            checkRights(itemId, authorizeAccount);
        }

    }


    private void checkRights(Long itemId, Account account) {
        if (!itemId.equals(account.getId())
                && account.getRoleList().stream().map(Role::getRoleType)
                .noneMatch(type -> Set.of(RoleType.ROLE_MODERATOR, RoleType.ROLE_ADMIN).contains(type))) {

            log.info("AppAspect Нет прав на редактирование");
            throw new AccessRightsException("Нет прав на редактирование");
        }
    }


}
