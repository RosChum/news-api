package com.example.newsapi.service;

import com.example.newsapi.dto.NewsCategoryDto;
import com.example.newsapi.mapper.NewsCategoryMapper;
import com.example.newsapi.model.NewsСategory;
import com.example.newsapi.repository.NewsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsCategoryMapper newsCategoryMapper;

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


}
