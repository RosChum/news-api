package com.example.newsapi;

import com.example.newsapi.dto.AuthenticationUserDto;
import com.example.newsapi.model.Account;
import com.example.newsapi.model.Role;
import com.example.newsapi.model.RoleType;
import com.example.newsapi.repository.AccountRepository;
import com.example.newsapi.repository.RoleRepository;
import com.example.newsapi.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public void run(String... args) throws Exception {

//        Add roles
        List<Role> roleList = List.of(
                new Role(null, RoleType.ROLE_USER, new HashSet<>())
                , new Role(null, RoleType.ROLE_ADMIN, new HashSet<>())
                , new Role(null, RoleType.ROLE_MODERATOR, new HashSet<>()));

        roleRepository.saveAll(roleList);

//      Add Admin account
        AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto("Admin", null,
                "mail@mail.ru", "12345", Set.of(new Role(null,RoleType.ROLE_ADMIN, Collections.EMPTY_SET)));
        securityService.register(authenticationUserDto);

    }



}
