package com.example.newsapi.service;

import com.example.newsapi.annotation.CheckAccessRights;
import com.example.newsapi.dto.NewsDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.exception.ContentNotFoundException;
import com.example.newsapi.mapper.AccountMapper;
import com.example.newsapi.mapper.CommentMapper;
import com.example.newsapi.mapper.NewsCategoryMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.Account;
import com.example.newsapi.model.Account_;
import com.example.newsapi.model.News;
import com.example.newsapi.model.News_;
import com.example.newsapi.repository.AccountRepository;
import com.example.newsapi.repository.CommentRepository;
import com.example.newsapi.repository.NewsRepository;
import com.example.newsapi.specifacation.BaseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;

import static com.example.newsapi.specifacation.BaseSpecification.getBetween;
import static com.example.newsapi.specifacation.BaseSpecification.getLike;

@Service
@RequiredArgsConstructor
public class NewsService implements BaseService<NewsDto> {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsCategoryMapper newsCategoryMapper;
    private final NewsCategoryService newsCategoryService;


    @Override
    public Page<NewsDto> findAll(SearchDto searchDto, Pageable pageable) {
        Page<News> newsPage = newsRepository.findAll(getSpecification(searchDto), pageable);
        return new PageImpl<>(newsPage.map(news -> {
            NewsDto newsDto = new NewsDto();
            newsDto.setNewsCategory(newsCategoryMapper.convertToListDto(news.getNewsСategoryList()));
            newsDto.setId(news.getId());
            newsDto.setTitle(news.getTitle());
            newsDto.setDescription(news.getDescription());
            newsDto.setCreateTime(news.getCreateTime());
            newsDto.setCountComment(commentRepository.countByNews_Id(news.getId()));
            newsDto.setUpdateTime(news.getUpdateTime());
            newsDto.setShortAccountDto(accountMapper.convertToShortDto(news.getAccount()));
            return newsDto;
        }).toList(), pageable, newsPage.getTotalElements());
    }

    @Override
    public NewsDto findById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Новость с id {0} не найдена", id)));
        NewsDto newsDto = newsMapper.convertToDto(news);
        newsDto.setShortAccountDto(accountMapper.convertToShortDto(news.getAccount()));
        newsDto.setNewsCategory(newsCategoryMapper.convertToListDto(news.getNewsСategoryList()));
        newsDto.setCommentDtos(commentMapper.convertToListDto(news.getCommentList()));
        return newsDto;
    }

    @Override
    public NewsDto create(NewsDto dto) {
        News news = newsMapper.convertToEntity(dto);
        Account account = accountRepository.findById(SecurityService.getAuthenticationUserId()).orElseThrow();
        news.setAccount(account);
        account.getNews().add(news);
        news.setNewsСategoryList(newsCategoryMapper.convertToListEntity(newsCategoryService.create(dto.getNewsCategory())));
        news.setCommentList(new ArrayList<>());
        NewsDto newsDto = newsMapper.convertToDto(newsRepository.save(news));
        newsDto.setShortAccountDto(accountMapper.convertToShortDto(account));
        newsDto.setNewsCategory(newsCategoryMapper.convertToListDto(news.getNewsСategoryList()));
        return newsDto;
    }

    @Transactional
    @Override
    public NewsDto update(Long id, NewsDto dto) {
        News existNews = newsRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Новость с id {0} для обновления  не найдена", id)));
        existNews.setTitle(dto.getTitle());
        existNews.setDescription(dto.getDescription());
        existNews.setUpdateTime(Instant.now());
        NewsDto resultDto = newsMapper.convertToDto(newsRepository.save(existNews));
        resultDto.setShortAccountDto(accountMapper.convertToShortDto(accountRepository.findById(SecurityService.getAuthenticationUserId()).orElseThrow()));
        resultDto.setNewsCategory(newsCategoryMapper.convertToListDto(existNews.getNewsСategoryList()));
        return resultDto;
    }


    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    private Specification<News> getSpecification(SearchDto searchDto) {
        return getLike(News_.title, searchDto.getTitleNews())
                .and(getLike(News_.description, searchDto.getDescriptionNews()))
                .and(getBetween(News_.createTime, searchDto.getTimeFrom() != null ? Instant.parse(searchDto.getTimeFrom()) : null,
                        searchDto.getTimeTo() != null ? Instant.parse(searchDto.getTimeTo()) : null))
                .and(BaseSpecification.getInCategoryNews(searchDto.getNewsCategory()))
                .and(BaseSpecification.joinAuthors(Account_.LAST_NAME, searchDto.getLastNameAuthor()))
                .and(BaseSpecification.joinAuthors(Account_.FIRST_NAME, searchDto.getFirstNameAuthor()));
    }

}
