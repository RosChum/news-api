package com.example.newsapi.service;

import com.example.newsapi.dto.AuthenticationResponseDto;
import com.example.newsapi.dto.AuthenticationUserDto;
import com.example.newsapi.dto.RefreshTokenDto;
import com.example.newsapi.exception.RefreshTokenException;
import com.example.newsapi.exception.UserNotFoundException;
import com.example.newsapi.mapper.UserMapper;
import com.example.newsapi.model.LoginDto;
import com.example.newsapi.model.RefreshToken;
import com.example.newsapi.model.User;
import com.example.newsapi.repository.UserRepository;
import com.example.newsapi.security.AppUserDetails;
import com.example.newsapi.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final UserMapper userMapper;

    public AuthenticationResponseDto authentication(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        String accessToken = jwtProvider.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createTokenByUserId(userDetails.getUserId());

        return new AuthenticationResponseDto(userDetails.getUserId(), accessToken, refreshToken.getToken());
    }

    public void register(AuthenticationUserDto authenticationUserDto) {
        User user = userMapper.convertFromAuthDtoToEntity(authenticationUserDto);
        user.setPassword(passwordEncoder.encode(authenticationUserDto.getPassword()));
        user.setCreateTime(Instant.now());
        user.setRoleList(authenticationUserDto.getRoles().stream().peek(role ->
                role.getUsers().add(user)).collect(Collectors.toSet()));
        userRepository.save(user);
    }

    public RefreshTokenDto refreshToken(RefreshTokenDto refreshTokenDto) {

        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenDto.getRefreshToken());
        if (refreshTokenService.checkExpireRefreshToken(refreshToken)) {
            User user = userRepository.findById(refreshToken.getUserId()).orElseThrow(() -> new UserNotFoundException(
                    MessageFormat.format("User with refresh token: {0} not found", refreshToken.getToken())));

            refreshTokenService.deleteByUserId(user.getId());
            RefreshToken updateRefreshToken = refreshTokenService.createTokenByUserId(user.getId());
            String updateAccessToken = jwtProvider.generateToken((AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            return new RefreshTokenDto(updateAccessToken, updateRefreshToken.getToken());
        }

        throw new RefreshTokenException("Exception trying to get token");
    }

    public static Long getAuthenticationUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    public void logout() {
        refreshTokenService.deleteByUserId(getAuthenticationUserId());
    }


}
