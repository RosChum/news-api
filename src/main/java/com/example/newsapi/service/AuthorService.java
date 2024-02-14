package com.example.newsapi.service;

import com.example.newsapi.dto.AuthorDto;
import com.example.newsapi.exception.ContentNotFound;
import com.example.newsapi.mapper.AuthorMapper;
import com.example.newsapi.model.Author;
import com.example.newsapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements BaseService<AuthorDto> {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto findById(Long id) {
        return authorMapper.convertToDto(authorRepository.findById(id)
                .orElseThrow(() -> new ContentNotFound(MessageFormat.format("Автор с id {0} не найден", id))));
    }

    @Override
    public List<AuthorDto> getAll() {
        return authorMapper.convertToListDto(authorRepository.findAll());
    }

    @Override
    public AuthorDto create(AuthorDto dto) {
        return authorMapper.convertToDto(authorRepository.save(authorMapper.convertToEntity(dto)));
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
}
