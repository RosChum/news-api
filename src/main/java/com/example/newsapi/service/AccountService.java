package com.example.newsapi.service;

import com.example.newsapi.dto.AccountDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.exception.ContentNotFoundException;
import com.example.newsapi.mapper.AccountMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.Account;
import com.example.newsapi.model.Account_;
import com.example.newsapi.model.User;
import com.example.newsapi.repository.AccountRepository;
import com.example.newsapi.repository.UserRepository;
import com.example.newsapi.specifacation.BaseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService implements BaseService<AccountDto> {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final NewsMapper newsMapper;
    private final UserRepository userRepository;

    @Override
    public Page<AccountDto> findAll(SearchDto searchDto, Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(getSpecification(searchDto), pageable);
        return new PageImpl<>(accountPage.map(account -> {
            AccountDto accountDto = new AccountDto();
            accountDto.setId(account.getId());
            accountDto.setFirstName(account.getFirstName());
            accountDto.setLastName(account.getLastName());
            accountDto.setCreateTime(account.getCreateTime().toString());
            accountDto.setNews(newsMapper.convertToShortDto(account.getNews()));
            return accountDto;
        }).toList(), pageable, accountPage.getTotalElements());
    }

    @Override
    public AccountDto findById(Long id) {
        return accountMapper.convertToDto(accountRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Аккаунт с id {0} не найден", id))));
    }


    @Override
    public AccountDto create(AccountDto dto) {
        return accountMapper.convertToDto(accountRepository.save(accountMapper.createEntity(dto)));
    }

    @Override
    public AccountDto update(Long id, AccountDto dto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Аккаунт с id {0} не найден", id)));
        account.setLastName(dto.getLastName());
        account.setFirstName(dto.getFirstName());
        return accountMapper.convertToDto(accountRepository.save(account));
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);

    }

    public Account createByUser(User user) {
         Account account = accountMapper.convertFromUserToEntity(user);
         account.setRoleList(user.getRoleList());
         account.setNews(new ArrayList<>());
         account.setComments(new ArrayList<>());
        return accountRepository.save(account);

    }

    private Specification getSpecification(SearchDto searchDto) {

        return BaseSpecification.getAccountSpecification(searchDto)
                .and(BaseSpecification.getEqual(Account_.firstName, searchDto.getFirstNameAuthor()))
                .and(BaseSpecification.getEqual(Account_.lastName, searchDto.getLastNameAuthor()))
                .and(BaseSpecification.getBetween(Account_.createTime, searchDto.getTimeFrom() != null ?
                                Instant.parse(searchDto.getTimeFrom()) : null
                        , searchDto.getTimeTo() != null ? Instant.parse(searchDto.getTimeTo()) : null))
                .and(BaseSpecification.joinNews(searchDto.getAuthorId()));
    }

}
