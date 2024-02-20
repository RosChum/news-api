package com.example.newsapi.service;

import com.example.newsapi.dto.AuthorDto;
import com.example.newsapi.dto.SearchDto;
import com.example.newsapi.exception.ContentNotFound;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.mapper.NewsMapper;
import com.example.newsapi.model.Author;
import com.example.newsapi.model.Author_;
import com.example.newsapi.repository.AuthorRepository;
import com.example.newsapi.specifacation.BaseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthorService implements BaseService<AuthorDto> {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final NewsMapper newsMapper;

    @Override
    public Page<AuthorDto> findAll(SearchDto searchDto, Pageable pageable) {
        Page<Author> authorPage = authorRepository.findAll(getSpecification(searchDto), pageable);
        return new PageImpl<>(authorPage.map(author -> {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(author.getId());
            authorDto.setFirstName(author.getFirstName());
            authorDto.setLastName(author.getLastName());
            authorDto.setCreateTime(author.getCreateTime().toString());
            authorDto.setNews(newsMapper.convertToShortDto(author.getNews()));
            return authorDto;
        }).toList(), pageable, authorPage.getTotalElements());
    }

    @Override
    public AuthorDto findById(Long id) {
        return authorMapper.convertToDto(authorRepository.findById(id)
                .orElseThrow(() -> new ContentNotFound(MessageFormat.format("Автор с id {0} не найден", id))));
    }


    @Override
    public AuthorDto create(AuthorDto dto) {
        return authorMapper.convertToDto(authorRepository.save(authorMapper.createEntity(dto)));
    }

    @Override
    public AuthorDto update(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ContentNotFound(MessageFormat.format("Автор с id {0} не найден", id)));
        author.setLastName(dto.getLastName());
        author.setFirstName(dto.getFirstName());
        return authorMapper.convertToDto(authorRepository.save(author));
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);

    }

    private Specification<Author> getSpecification(SearchDto searchDto) {
        return BaseSpecification.getEqual(Author_.firstName, searchDto.getFirstNameAuthor())
                .and(BaseSpecification.getEqual(Author_.lastName, searchDto.getLastNameAuthor()))
                .and(BaseSpecification.getBetween(Author_.createTime, searchDto.getTimeFrom() != null ? Instant.parse(searchDto.getTimeFrom()) : null
                        , searchDto.getTimeTo() != null ? Instant.parse(searchDto.getTimeTo()) : null))
                .and(BaseSpecification.joinNews(searchDto.getAuthorId()));
    }
}
