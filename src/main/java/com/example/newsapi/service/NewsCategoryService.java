package com.example.newsapi.service;

import com.example.newsapi.dto.NewsCategoryDto;
import com.example.newsapi.exception.ContentNotFoundException;
import com.example.newsapi.mapper.NewsCategoryMapper;
import com.example.newsapi.model.NewsСategory;
import com.example.newsapi.repository.NewsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsCategoryMapper newsCategoryMapper;

    public Page<NewsCategoryDto> findAll(Pageable pageable) {
        Page<NewsСategory> newsСategoryPage = newsCategoryRepository.findAll(pageable);
        return new PageImpl<>(newsСategoryPage.map(newsСategory -> {
            NewsCategoryDto newsCategoryDto = new NewsCategoryDto();
            newsCategoryDto.setNewsCategory(newsСategory.getNewsCategory());
            newsCategoryDto.setId(newsСategory.getId());
            return newsCategoryDto;
        }).toList(), pageable, newsСategoryPage.getTotalElements());
    }

    public NewsCategoryDto findById(Long id) {
        return newsCategoryMapper.convertToDto(newsCategoryRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Категория новости с id {0} не найдена", id))));
    }

    public List<NewsCategoryDto> create(List<NewsCategoryDto> dtos) {
        List<NewsСategory> existNewsCategory = newsCategoryRepository
                .findAllByNewsCategoryIn(dtos.stream().map(NewsCategoryDto::getNewsCategory).collect(Collectors.toList()));
        List<NewsСategory> convertDtosToEntity = newsCategoryMapper.convertToListEntity(dtos);
        List<NewsСategory> newCategory = new ArrayList<>();
        convertDtosToEntity.forEach(newsСategory -> {
            if (!existNewsCategory.contains(newsСategory)) {
                newCategory.add(newsСategory);
            }
        });
        newsCategoryRepository.saveAll(newCategory);
        newCategory.addAll(existNewsCategory);
        return newsCategoryMapper.convertToListDto(newCategory);
    }

    public NewsCategoryDto update(NewsCategoryDto newsCategoryDto) {
        NewsСategory newsСategory = newsCategoryRepository.findById(newsCategoryDto.getId())
                .orElseThrow(() -> new ContentNotFoundException(MessageFormat.format("Категория новости с id {0} не найдена", newsCategoryDto.getId())));
        newsСategory.setNewsCategory(newsCategoryDto.getNewsCategory());
        return newsCategoryMapper.convertToDto(newsCategoryRepository.save(newsСategory));
    }

    public void delete(Long id) {
        newsCategoryRepository.deleteById(id);
    }


}
