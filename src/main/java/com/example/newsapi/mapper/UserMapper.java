package com.example.newsapi.mapper;

import com.example.newsapi.dto.AuthenticationUserDto;
import com.example.newsapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roleList", ignore = true)
    User convertFromAuthDtoToEntity(AuthenticationUserDto authenticationUserDto);
}
