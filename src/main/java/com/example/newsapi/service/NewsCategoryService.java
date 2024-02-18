package com.example.newsapi.service;

import com.example.newsapi.dto.NewsCategoryDto;
import com.example.newsapi.mapper.NewsCategoryMapper;
import com.example.newsapi.repository.NewsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsCategoryMapper newsCategoryMapper;
    //TODO убрать повторения
    private List<NewsCategoryDto> create(List<NewsCategoryDto> dtos){
        return newsCategoryMapper.convertToListDto(newsCategoryRepository.saveAll(newsCategoryMapper.convertToListEntity(dtos)));
    }

}
