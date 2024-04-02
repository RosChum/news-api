package com.example.newsapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class AuthDto {

    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;


    private String lastName;

    @Email(message = "введен некорректный email")
    private String email;

    private String password;
}
