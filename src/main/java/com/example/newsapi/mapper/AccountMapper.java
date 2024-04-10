package com.example.newsapi.mapper;

import com.example.newsapi.dto.AccountDto;
import com.example.newsapi.dto.ShortAccountDto;
import com.example.newsapi.model.Account;
import com.example.newsapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface AccountMapper {


    @Mapping(target = "createTime", expression = "java(Instant.now())")
    @Mapping(target = "news", expression = "java(account.getNews() == null ? new ArrayList<>():account.getNews())")
    @Mapping(target = "id", ignore = true)
    Account createEntity(AccountDto accountDto);

    Account convertToEntity(AccountDto accountDto);


    @Mapping(target = "createTime", expression = "java(Instant.now())")
    Account convertFromUserToEntity(User user);

    AccountDto convertToDto(Account account);

    ShortAccountDto convertToShortDto(Account account);

    AccountDto shortAuthorDtoConvertToDto(ShortAccountDto shortAccountDto);

    List<AccountDto> convertToListDto(List<Account> accountList);
}
